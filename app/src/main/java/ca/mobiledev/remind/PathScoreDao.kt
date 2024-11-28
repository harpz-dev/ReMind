package ca.mobiledev.remind

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PathScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public fun insertPathScore(pathScore: PathScore): Long

    @Query("SELECT * FROM path_score ORDER BY attemptNo DESC")
     fun getAllPathScores(): LiveData<List<PathScore>>

 /*   @Query("SELECT * FROM path_score WHERE attemptNo = :id LIMIT 1")
     fun getHighestScore(): PathScore?*/

}
