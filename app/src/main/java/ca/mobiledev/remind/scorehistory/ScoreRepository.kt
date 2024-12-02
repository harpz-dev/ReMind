package ca.mobiledev.remind.scorehistory

import android.app.Application
import androidx.lifecycle.LiveData
import ca.mobiledev.remind.scorehistory.PathScoreDatabase.Companion.getDatabase

class ScoreRepository(application: Application) {

    private val pathScoreDao: PathScoreDao? = getDatabase(application).PathScoreDao()

    // TODO Add query specific methods
    //  HINT 1:
    //   The insert operation will use the Runnable interface as there are no return values
    val allPathScores: LiveData<List<PathScore>> = pathScoreDao!!.getAllPathScores()

    val topFivePathScores: LiveData<List<PathScore>> = pathScoreDao!!.getTopFivePathwaysScore()

    val pathHighScore: LiveData<Int> = pathScoreDao!!.getHighestPathwaysScore()

    val allChimpScores: LiveData<List<PathScore>> = pathScoreDao!!.getAllChimpScores()

    val topFiveChimpScores: LiveData<List<PathScore>> = pathScoreDao!!.getTopFiveChimpScore()

    val chimpHighScore: LiveData<Int> = pathScoreDao!!.getHighestChimpScore()

    fun insertRecord(item: PathScore) {

        val newItem = PathScore(0, item.dateTime, item.score, item.gameID)

        insert(newItem)
    }

    private fun insert(item: PathScore) {
        // Using a Runnable thread object as there are no return values
        PathScoreDatabase.databaseWriterExecutor.execute { pathScoreDao!!.insertPathScore(item) }
    }
}