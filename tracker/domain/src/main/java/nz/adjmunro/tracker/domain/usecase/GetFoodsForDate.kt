package nz.adjmunro.tracker.domain.usecase

import kotlinx.coroutines.flow.Flow
import nz.adjmunro.tracker.domain.model.TrackedFood
import nz.adjmunro.tracker.domain.repository.TrackerRepository
import java.time.LocalDate

class GetFoodsForDate(
    private val repository: TrackerRepository,
) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(localDate = date)
    }
}
