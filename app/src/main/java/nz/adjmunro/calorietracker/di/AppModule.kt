package nz.adjmunro.calorietracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nz.adjmunro.core.data.preferences.DefaultPreferences
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.domain.usecase.FilterOnlyDigits
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences("shared_pref", MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(
        sharedPreferences: SharedPreferences,
    ): Preferences = DefaultPreferences(
        sharedPref = sharedPreferences
    )

    @Provides
    @Singleton
    fun providesFilterOnlyDigitsUseCase(): FilterOnlyDigits = FilterOnlyDigits()

}
