package ca.mobiledev.remind

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.nio.file.Path
import java.sql.Time

class PathScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val pathScoreRepository: PathScoreRepository = PathScoreRepository(application)

    // TODO
    //  Add mapping calls between the UI and Database

    fun insert(dateTime:String, score:Int, timeTaken: Long, levelReached: Int){

        pathScoreRepository.insertRecord(PathScore(0,dateTime, score, timeTaken, levelReached))

    }
    fun getHighestScore(): LiveData<List<PathScore>> {
        return pathScoreRepository.allItems
    }

    fun getAverageScore(): LiveData<Float>{
        return pathScoreRepository.averageScore
    }

}