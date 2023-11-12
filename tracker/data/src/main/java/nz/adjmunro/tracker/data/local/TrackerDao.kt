package nz.adjmunro.tracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nz.adjmunro.tracker.data.local.entity.TrackedFoodEntity

@Dao
interface TrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFood: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFood: TrackedFoodEntity)

    @Query(
        """
        SELECT * 
        FROM trackedfoodentity
        WHERE dayOfMonth = :day AND month = :month AND year = :year
        """
    )
    fun getFoodsForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}
