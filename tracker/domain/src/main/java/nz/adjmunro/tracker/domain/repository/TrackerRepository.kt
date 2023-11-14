package nz.adjmunro.tracker.domain.repository

import kotlinx.coroutines.flow.Flow
import nz.adjmunro.tracker.domain.model.TrackableFood
import nz.adjmunro.tracker.domain.model.TrackedFood
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>>

    suspend fun insertTrackedFood(food: TrackedFood)

    suspend fun deleteTrackedFood(food: TrackedFood)

    fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>>
}
