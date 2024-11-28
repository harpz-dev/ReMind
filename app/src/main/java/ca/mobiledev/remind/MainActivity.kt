package ca.mobiledev.remind

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
          layoutInflater.inflate(R.layout.activity_main, findViewById(R.id.content_frame))

        val startButton = findViewById<Button>(R.id.btnStart)
        startButton.setBackgroundColor(resources.getColor(R.color.blue))
        startButton.setTextColor(resources.getColor(R.color.white))
        startButton.setOnClickListener {
            val intent = Intent(applicationContext, Game::class.java)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Log.e(TAG, "Unable to start the activity")
            }
        }
        }

    }
