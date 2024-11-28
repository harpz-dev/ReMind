package ca.mobiledev.remind

import android.os.Bundle
import android.widget.ListView
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.mobiledev.remind.databinding.ActivityScoreHistoryBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreHistory : BaseActivity() {

    private lateinit var itemViewModel: PathScoreViewModel
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_score_history, findViewById(R.id.content_frame))

        listView= findViewById(R.id.listview)


        itemViewModel = ViewModelProvider(this)[PathScoreViewModel::class.java]

        itemViewModel.listAll().observe(this, Observer{ items->
            var adapter =
                PathScoreAdapter(this, items)
            listView.adapter=adapter
        })

        itemViewModel.insert("28thNovember", 10, 20)
    }




}