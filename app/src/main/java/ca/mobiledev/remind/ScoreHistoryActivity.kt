package ca.mobiledev.remind

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreHistoryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        layoutInflater.inflate(R.layout.activity_score_history, findViewById(R.id.content_frame))

    }
}