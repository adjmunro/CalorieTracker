package nz.adjmunro.tracker.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nz.adjmunro.tracker.data.local.TrackerDao
import nz.adjmunro.tracker.data.mapper.toTrackableFood
import nz.adjmunro.tracker.data.mapper.toTrackedFood
import nz.adjmunro.tracker.data.mapper.toTrackedFoodEntity
import nz.adjmunro.tracker.data.remote.OpenFoodApi
import nz.adjmunro.tracker.domain.model.TrackableFood
import nz.adjmunro.tracker.domain.model.TrackedFood
import nz.adjmunro.tracker.domain.repository.TrackerRepository
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi,
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                searchTerms = query,
                page = page,
                pageSize = pageSize,
            )
            Result.success(searchDto.products.mapNotNull { it.toTrackableFood() })

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> =
        dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year,
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
}
