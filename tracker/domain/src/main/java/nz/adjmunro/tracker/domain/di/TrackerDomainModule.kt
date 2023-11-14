package nz.adjmunro.tracker.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.tracker.domain.repository.TrackerRepository
import nz.adjmunro.tracker.domain.usecase.CalculateMealNutrients
import nz.adjmunro.tracker.domain.usecase.DeleteTrackedFood
import nz.adjmunro.tracker.domain.usecase.GetFoodsForDate
import nz.adjmunro.tracker.domain.usecase.SearchFood
import nz.adjmunro.tracker.domain.usecase.TrackFood
import nz.adjmunro.tracker.domain.usecase.TrackerUseCases

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {
    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences,
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences),
        )
    }
}
