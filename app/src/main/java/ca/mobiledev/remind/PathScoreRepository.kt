package ca.mobiledev.remind

import android.app.Application
import androidx.lifecycle.LiveData
import ca.mobiledev.remind.PathScoreDatabase.Companion.getDatabase

class PathScoreRepository(application: Application) {

    private val pathScoreDao: PathScoreDao? = getDatabase(application).PathScoreDao()

    // TODO Add query specific methods
    //  HINT 1:
    //   The insert operation will use the Runnable interface as there are no return values
    val allItems: LiveData<List<PathScore>> = pathScoreDao!!.getAllPathScores()

    val topFive: LiveData<List<PathScore>> = pathScoreDao!!.getTopFive()

    val highScore: LiveData<Int> = pathScoreDao!!.getHighestScore()

    fun insertRecord(item: PathScore) {

        val newItem = PathScore(0, item.dateTime, item.score, item.timeTaken)

        insert(newItem)
    }

    private fun insert(item: PathScore) {
        // Using a Runnable thread object as there are no return values
        PathScoreDatabase.databaseWriterExecutor.execute { pathScoreDao!!.insertPathScore(item) }
    }
}