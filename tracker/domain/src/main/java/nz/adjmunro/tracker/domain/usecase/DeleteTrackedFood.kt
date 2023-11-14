package nz.adjmunro.tracker.domain.usecase

import nz.adjmunro.tracker.domain.model.TrackedFood
import nz.adjmunro.tracker.domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(trackedFood: TrackedFood) {
        return repository.deleteTrackedFood(food = trackedFood)
    }
}
