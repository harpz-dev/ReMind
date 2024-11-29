package ca.mobiledev.remind

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PathScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertPathScore(pathScore: PathScore): Long

    @Query("SELECT * FROM path_score ORDER BY attemptNo DESC")
     fun getAllPathScores(): LiveData<List<PathScore>>

     @Query("SELECT * FROM path_score ORDER BY score DESC LIMIT 1")
     fun getHighestScore(): LiveData<PathScore?>

     @Query("SELECT AVG(score) FROM path_score")
     fun getAverageScore(): LiveData<Float>

 /*   @Query("SELECT * FROM path_score WHERE attemptNo = :id LIMIT 1")
     fun getHighestScore(): PathScore?*/

}
