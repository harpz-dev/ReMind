package ca.mobiledev.remind

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import androidx.annotation.InspectableProperty
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class Game : AppCompatActivity(){

    var userButtons = ArrayList<AppCompatButton>()
    var gameButtons = ArrayList<AppCompatButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_main, findViewById(R.id.gameGrid))
        setContentView(R.layout.activity_game)

        gameButtons.add(findViewById(R.id.btnRound1))
        var button = findViewById<AppCompatButton>(R.id.btnRound1)
        button.setBackgroundResource(R.drawable.round_button_pressed)

        //gameButtons.add(findViewById(R.id.btnRound8))
        gameButtons.add(findViewById(R.id.btnRound15))
        button = findViewById<AppCompatButton>(R.id.btnRound15)
        button.setBackgroundResource(R.drawable.round_button_pressed)


    }

    fun onClick(v: View){
        for (i in 0..29){
            val button = findViewById<AppCompatButton>((R.id.btnRound1+i))
            var counter = 0
            button.setOnClickListener {
                if(button.isPressed) {
                    counter += 1
                }
                if(counter%2==1){
                    button.setBackgroundResource(R.drawable.round_button_pressed)
                    userButtons.add(button)
                }
                else{
                    button.setBackgroundResource(R.drawable.round_button)
                    userButtons.remove(button)
                }
            }
        }
    }

    fun compareList(v: View){

        val submitButton = findViewById<AppCompatButton>(R.id.btnSubmit)
        submitButton.setOnClickListener{

            if(!gameButtons.containsAll(userButtons) || !userButtons.containsAll(gameButtons)){
                val popup = PopupWindow()
                popup.showAtLocation(v, Gravity.TOP,10,10)
                Log.w("Not in list", "Not in list")
            }
            else{
                Log.w("All good", "All good")
                }

        }

    }
}