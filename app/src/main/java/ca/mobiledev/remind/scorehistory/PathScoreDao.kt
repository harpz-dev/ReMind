package ca.mobiledev.remind.scorehistory

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PathScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPathScore(pathScore: PathScore): Long

    @Query("SELECT * FROM path_score ORDER BY attemptNo DESC")
     fun getAllPathScores(): LiveData<List<PathScore>>

     @Query("SELECT score, attemptNo, gameID, MAX(dateTime) as dateTime \n" +
             "    FROM path_score \n" +
             "    WHERE gameID = 'PathwaysGame'"+
             "    GROUP BY score \n" +
             "    ORDER BY score DESC, dateTime DESC \n" +
             "    LIMIT 5"
     )
     fun getTopFivePathwaysScore(): LiveData<List<PathScore>>

     @Query("SELECT MAX(score) FROM path_score WHERE gameID='PathwaysGame'")
     fun getHighestPathwaysScore(): LiveData<Int>


    @Query("SELECT * FROM path_score WHERE gameID= 'ChimpGame' ORDER BY attemptNo DESC")
    fun getAllChimpScores(): LiveData<List<PathScore>>

    @Query("SELECT score, attemptNo, gameID, MAX(dateTime) as dateTime \n" +
            "    FROM path_score \n" +
            "    WHERE gameID = 'ChimpGame'"+
            "    GROUP BY score \n" +
            "    ORDER BY score DESC, dateTime DESC \n" +
            "    LIMIT 5"
    )
    fun getTopFiveChimpScore(): LiveData<List<PathScore>>

    @Query("SELECT MAX(score) FROM path_score WHERE gameID='ChimpGame'")
    fun getHighestChimpScore(): LiveData<Int>


}
