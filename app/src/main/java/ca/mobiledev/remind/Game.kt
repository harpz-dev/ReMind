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
import androidx.appcompat.widget.AppCompatButton

class Game : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_game, findViewById(R.id.content_frame))
        //setContentView(R.layout.activity_game)

        for (i in 1..42){

            val buttonId= "btnRound$i"
            val resId= resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            var counter = 0
            button.setOnClickListener {
                if(button.isPressed) {
                    counter += 1
                }
                if(counter%2==1){
                    button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))
                //  button.setBackgroundColor(resources.getColor(R.color.blue))
                }
                else{
                    button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button))
                }
            }
        }
    }

    fun onClick(v: View){


    }
}