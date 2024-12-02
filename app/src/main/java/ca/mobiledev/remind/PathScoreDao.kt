package ca.mobiledev.remind

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PathScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPathScore(pathScore: PathScore): Long

    @Query("SELECT * FROM path_score ORDER BY attemptNo DESC")
     fun getAllPathScores(): LiveData<List<PathScore>>

     @Query("SELECT score, attemptNo, timeTaken, MAX(dateTime) as dateTime \n" +
             "    FROM path_score \n" +
             "    GROUP BY score \n" +
             "    ORDER BY score DESC, dateTime DESC \n" +
             "    LIMIT 5")
     fun getTopFive(): LiveData<List<PathScore>>

     @Query("SELECT MAX(score) FROM path_score")
     fun getHighestScore(): LiveData<Int>

}
