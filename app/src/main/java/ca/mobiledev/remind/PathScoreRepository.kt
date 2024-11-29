package ca.mobiledev.remind

import android.app.Application
import androidx.lifecycle.LiveData
import ca.mobiledev.remind.PathScoreDatabase.Companion.getDatabase
import java.nio.file.Path

class PathScoreRepository(application: Application) {

    private val pathScoreDao: PathScoreDao? = getDatabase(application).PathScoreDao()
    val allItems: LiveData<List<PathScore>> = pathScoreDao!!.getAllPathScores()
    val highestScore: LiveData<PathScore?> = pathScoreDao!!.getHighestScore()
    val averageScore: LiveData<Float> = pathScoreDao!!.getAverageScore()

    // TODO Add query specific methods
    //  HINT 1:
    //   The insert operation will use the Runnable interface as there are no return values


    fun insertRecord(item: PathScore) {

        val newItem = PathScore(
            0,
            item.dateTime,
            item.score,
            item.timeTaken,
            item.levelReached
        )
        insert(newItem)
    }

    private fun insert(item: PathScore) {
        // Using a Runnable thread object as there are no return values
        PathScoreDatabase.databaseWriterExecutor.execute { pathScoreDao!!.insertPathScore(item) }
    }



}