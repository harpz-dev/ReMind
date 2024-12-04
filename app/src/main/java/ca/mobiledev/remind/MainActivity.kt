package ca.mobiledev.remind

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatDelegate
import ca.mobiledev.remind.abstractclasses.BaseActivity
import ca.mobiledev.remind.pathwaysgame.Game

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
          layoutInflater.inflate(R.layout.activity_main, findViewById(R.id.content_frame))

        //set dark/light mode based on stored setting in shared pref
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val savedMode = sharedPreferences.getString("Theme", "system")
        val currentMode = when (savedMode) {
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(currentMode)

        val pathwaysButton = findViewById<ImageButton>(R.id.btnPathways)
        pathwaysButton.setBackgroundColor(resources.getColor(R.color.blue))
        //startButton.setTextColor(resources.getColor(R.color.white))
        pathwaysButton.setOnClickListener {
            val intent = Intent(applicationContext, ca.mobiledev.remind.pathwaysgame.Game::class.java)
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Log.e(TAG, "Unable to start the activity")
            }
        }

        val chimpgameButton = findViewById<ImageButton>(R.id.btnChimpgame)
        chimpgameButton.setBackgroundColor(resources.getColor(R.color.blue))
        chimpgameButton.setOnClickListener {
            val intent = Intent(applicationContext, ca.mobiledev.remind.chimpgame.Game::class.java)
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Log.e(TAG, "Unable to start the activity")
            }
        }





        }

    }
