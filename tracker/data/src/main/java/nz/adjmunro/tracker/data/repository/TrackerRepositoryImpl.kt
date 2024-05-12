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

            val food = searchDto.products.filter {
                // Filter out search results with inconsistent calorie counts
                val carbs = it.nutriments.carbohydrates100g * 4f
                val protein = it.nutriments.proteins100g * 4f
                val fat = it.nutriments.fat100g * 9f
                val acceptableBounds = (carbs + protein + fat).let { cal ->
                    (cal * 0.99f)..(cal * 1.01f)
                }

                it.nutriments.energyKcal100g in acceptableBounds
            }
                .mapNotNull { it.toTrackableFood() }

            Result.success(food)

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
        )
            .map { entities ->
                entities.map { it.toTrackedFood() }
            }
}
