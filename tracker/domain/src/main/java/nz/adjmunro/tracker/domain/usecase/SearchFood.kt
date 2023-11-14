package nz.adjmunro.tracker.domain.usecase

import nz.adjmunro.tracker.domain.model.TrackableFood
import nz.adjmunro.tracker.domain.repository.TrackerRepository

class SearchFood(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40,
    ): Result<List<TrackableFood>> {
        if (query.isBlank()) return Result.success(emptyList())

        return repository.searchFood(query = query.trim(), page = page, pageSize = pageSize)
    }
}
