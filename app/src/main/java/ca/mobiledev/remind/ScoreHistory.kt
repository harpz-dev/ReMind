package ca.mobiledev.remind

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class ScoreHistory : BaseActivity() {

    private lateinit var itemViewModel: PathScoreViewModel
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_score_history, findViewById(R.id.content_frame))
        val model: MazeModel = MazeModel()
        val level = model.getLevel()
        Log.d("level", "$level")
        listView= findViewById(R.id.listview)

        itemViewModel = ViewModelProvider(this)[PathScoreViewModel::class.java]

        itemViewModel.getTopFive().observe(this, Observer{ items->
            var adapter =
                PathScoreAdapter(this, items)
            listView.adapter=adapter
        })
    }
}