package ca.mobiledev.remind

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar


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

        val refresh = findViewById<AppCompatButton>(R.id.buttonRefresh)

        refresh.setOnClickListener{
            val i: Intent = Intent(this, Game::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(i)
            finish()
        }

        //setting up the onClickListeners for the buttons
        for (i in 1..42){
            val buttonId= "btnRound$i"
            val resId= resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
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

    fun onClick(v: View){}

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

    fun equal(solution: ArrayList<Int>, selected: ArrayList<Int>): Boolean{
        return selected.hashCode() == solution.hashCode()
    }

    fun compareList(v: View) {
        val submitButton = findViewById<AppCompatButton>(R.id.button2)
        submitButton.setOnClickListener {
            val solution = model.getSolution()
            val selected = model.getSelected()
                if (!equal(solution,selected)) {
                    model.decAttempts()
                    if(model.attempts == 0){
                        finish()
                    }
                    val popup = Snackbar.make(v, "${model.attempts} attempts remaining.", 1000)
                    popup.show()
                    Log.w("Not in list", "Not in list")
                    Log.w("solution", solution.toString())
                    Log.w("selected", selected.toString())
                } else {
                    Log.w("All good", "All good")
                    Log.w("solution", solution.toString())
                    Log.w("selected", selected.toString())
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