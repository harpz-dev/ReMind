package ca.mobiledev.remind

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.GridView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.annotation.InspectableProperty
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar

class Game : AppCompatActivity(){

    var userButtons = ArrayList<AppCompatButton>()
    var gameButtons = ArrayList<AppCompatButton>()
    lateinit var bg:RelativeLayout;

    enum class State{
        PREGAME, INGAME
    }

    var state:State = State.PREGAME


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_main, findViewById(R.id.gameGrid))
        setContentView(R.layout.activity_game)

        bg=findViewById(R.id.rl)

        for (i in 1..42){

            val buttonId= "btnRound$i"


            val resId= resources.getIdentifier(buttonId, "id", packageName)
            val button1 = findViewById<AppCompatButton>(resId)
            var counter = 0
            button1.setOnClickListener {
                if(state==State.PREGAME){
                    clearPregameColors()
                    state=State.INGAME
                }


                if(button1.isPressed) {
                    counter += 1
                }
                if(counter%2==1){
                    button1.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))
                    userButtons.add(button1)
                    //  button.setBackgroundColor(resources.getColor(R.color.blue))
                }
                else{
                    button1.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button))
                    userButtons.remove(button1)
                }
            }

            if(i%6==1 ||i in 38..42){
                gameButtons.add(button1)
                button1.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pregame))
            }

        }
        bg?.setOnClickListener {
            if(state==State.PREGAME)
            clearPregameColors()
        }


    }

    private fun clearPregameColors() {
        for (button in gameButtons) {
            button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button))
        }
    }


    fun onClick(v: View){
        var i=1
        for(i in 1..42){

                val buttonId = "btnRound$i"


                val resId = resources.getIdentifier(buttonId, "id", packageName)
                val button1 = findViewById<AppCompatButton>(resId)

                button1.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))

        }
    }

    fun compareList(v: View){
        val submitButton = findViewById<AppCompatButton>(R.id.btnSubmit)
        submitButton.setOnClickListener{
            if(!gameButtons.containsAll(userButtons) || !userButtons.containsAll(gameButtons)){
                val popup = Snackbar.make(v,"Try Again", 1000)
                popup.show()
                Log.w("Not in list", "Not in list")
            }
            else{
                Log.w("All good", "All good")
                val builder = AlertDialog.Builder(v.context)
                builder.setMessage("You won")
                    .setPositiveButton("Congrats") { dialog, id ->
                        // START THE GAME!
                    }
                    .setNegativeButton("Try again") { dialog, id ->
                        // User cancelled the dialog.
                    }
                // Create the AlertDialog object and return it.
                builder.create()
                builder.show()
            }
        }
    }
}