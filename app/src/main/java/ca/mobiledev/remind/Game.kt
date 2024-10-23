package ca.mobiledev.remind

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.InspectableProperty
import androidx.appcompat.app.AppCompatActivity

class Game : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_main, findViewById(R.id.gameGrid))
        setContentView(R.layout.activity_game)



    }

    fun onClick(v: View){

        val button = findViewById<Button>(v.id)
        button.setOnClickListener {
            if(button.isPressed){
                button.setBackgroundColor(Color.BLUE)
            }
            else{
                button.setBackgroundColor(Color.GRAY)
            }

        }
    }


}



