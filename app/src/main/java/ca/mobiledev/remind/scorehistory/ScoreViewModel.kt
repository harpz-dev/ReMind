package ca.mobiledev.remind.scorehistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val scoreRepository: ScoreRepository = ScoreRepository(application)

    //val highScore: LiveData<Int> = pathScoreRepository.getHighScore()

    // TODO
    //  Add mapping calls between the UI and Database

    fun insert(dateTime:String, score:Int, gameId: String){

        scoreRepository.insertRecord(PathScore(0,dateTime, score, gameId))

    }

    fun listAll(): LiveData<List<PathScore>>{
        return scoreRepository.allPathScores
    }

    fun getTopFive(): LiveData<List<PathScore>>{
        return scoreRepository.topFivePathScores
    }

    fun getHighScore(): LiveData<Int> {
        return scoreRepository.pathHighScore
    }

}