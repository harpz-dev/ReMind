package ca.mobiledev.remind

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.sql.Time

class PathScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val pathScoreRepository: PathScoreRepository = PathScoreRepository(application)

    //val highScore: LiveData<Int> = pathScoreRepository.getHighScore()

    // TODO
    //  Add mapping calls between the UI and Database

    fun insert(dateTime:String, score:Int, timeTaken: Long){

        pathScoreRepository.insertRecord(PathScore(0,dateTime, score, timeTaken))

    }

    fun listAll(): LiveData<List<PathScore>>{
        return pathScoreRepository.allItems
    }

    fun getTopFive(): LiveData<List<PathScore>>{
        return pathScoreRepository.topFive
    }

    fun getHighScore(): LiveData<Int> {
        return pathScoreRepository.highScore
    }

}