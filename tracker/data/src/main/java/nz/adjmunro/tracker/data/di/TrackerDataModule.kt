package nz.adjmunro.tracker.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nz.adjmunro.tracker.data.local.TrackerDatabase
import nz.adjmunro.tracker.data.remote.OpenFoodApi
import nz.adjmunro.tracker.data.repository.TrackerRepositoryImpl
import nz.adjmunro.tracker.domain.repository.TrackerRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrackerDataModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    @Provides
    @Singleton
    fun providesOpenFoodApi(client: OkHttpClient): OpenFoodApi = Retrofit.Builder()
        .baseUrl(OpenFoodApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create()

    @Provides
    @Singleton
    fun providesTrackerDatabase(
        @ApplicationContext context: Context,
    ): TrackerDatabase = Room.databaseBuilder(
        context = context,
        klass = TrackerDatabase::class.java,
        name = "tracker_db",
    ).build()

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDatabase,
    ): TrackerRepository = TrackerRepositoryImpl(
        dao = db.dao,
        api = api,
    )
}
