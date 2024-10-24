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

    private val model:MazeModel= MazeModel()

    //State machine for the view (PREGAME SHOWING THE SOLUTION VS INGAME SHOWING USER SELECTION
    enum class State{
        PREGAME, INGAME
    }

    var state: State= State.PREGAME


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_game, findViewById(R.id.content_frame))

        //setting up the onClickListeners for the buttons
        for (i in 1..42){
            val buttonId= "btnRound$i"
            val resId= resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            var counter = 0
            button.setOnClickListener {
                if(button.isPressed) {
                   model.addPoint(i)
                    draw()
                }
            }
        }

        //pregame state: call to draw solution
        draw()


        //end pregame state: next draw() will make solution disappear
        state= State.INGAME
    }

    fun onClick(v: View){


    }

    private fun draw(){
        //If state is pregame

        if(state==State.PREGAME){
         val solution: ArrayList<Int> = model.getSolution()

        for(i:Int in solution) {
            val buttonId = "btnRound$i"
            val resId = resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)

            button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))

        }
        }

        else if(state==State.INGAME){

            val selectedList: ArrayList<Int> = model.getSelected()

            for(i:Int in 1..42){

                val buttonId = "btnRound$i"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<AppCompatButton>(resId)

                if(selectedList.contains(i))
                    button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))
                else
                    button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button))



            }

        }


    }
}