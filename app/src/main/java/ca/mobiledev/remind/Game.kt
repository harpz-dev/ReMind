package ca.mobiledev.remind

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.iterator
import androidx.core.view.size
import com.google.android.material.snackbar.Snackbar
import java.util.stream.IntStream.range


class Game : BaseActivity(){

    private val model:MazeModel= MazeModel()

    private var solutionList: List<Int> = emptyList()// = model.getNextSolution()


    private lateinit var buttonGrid: List<AppCompatButton>


    //State machine for the view (PREGAME SHOWING THE SOLUTION VS INGAME SHOWING USER SELECTION
    enum class State{
        PREGAME, INGAME
    }

    private var state: State= State.PREGAME

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_game, findViewById(R.id.content_frame))

        val gridLayout: GridLayout= findViewById(R.id.gameGrid)


        var currentButton=-1 //no button selected by default
        gridLayout.setOnTouchListener { view, motionEvent ->
            val buttonNo= getChildAtXY(motionEvent.x, motionEvent.y, gridLayout)

            if(buttonNo!=-1) {

                if(buttonNo!=currentButton) {
                    Log.d("Found Button", "$buttonNo")
                    model.addPoint(buttonNo)
                    draw()
                    currentButton=buttonNo;
                }
            }

            true}


        //setting up the onClickListeners for the buttons
        for (i in 1..42){
            val buttonId= "btnRound$i"
            val resId= resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            button.isClickable=false

        }

        // Initialize the button grid once
        /*buttonGrid = (1..42).map { i ->
            val buttonId = "btnRound$i"
            val resId = resources.getIdentifier(buttonId, "id", packageName)

            findViewById<AppCompatButton>(resId)

        }*/

        //solutionList = model.getNext()

        //pregame state: call to draw solution
        draw()

        //end pregame state: next draw() will make solution disappear
        state= State.INGAME


    }

    //fun onClick(v: View){}

    fun clear(v:View){
        for (i in 1..42){
            val buttonId= "btnRound$i"
            val resId= resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button))

        }
    }


    fun getChildAtXY(x: Float, y: Float, gridLayout: GridLayout): Int {
        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            Log.d("GetChild", "MotionEvent x: $x y: $y  button x: ${view.left} ${view.right}  button y: ${view.top} ${view.bottom}")
            if (x > view.left && x < view.right && y > view.top && y < view.bottom) {
                return i+1
            }
        }
        return -1
    }


    fun refresh(v: View){
        clear(v)
        Log.d("points", "Refreshed game")

        state = State.PREGAME

        draw()
            //Log.d("points", "Refreshed game with new solution: $solutionList")
        state = State.INGAME
        //} else {
            //Log.d("points", "No solution available, generating a new path.")
        //}
            //val i: Intent = Intent(this, Game::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            //s/tartActivity(i)
            //finish()
    }

    /*fun draw() {
        when (state) {
            State.PREGAME -> {
                solutionList = model.getNextSolution()
                Log.d("points", "Refreshed game with new solution: $solutionList")
                // Reset all button states
                buttonGrid.forEach { it.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button)) }

                // Update only the buttons in the solution path
                for (i in solutionList) {
                    val button = buttonGrid[i - 1]  // Adjust for 1-based indexing
                    when (i) {
                        solutionList.first() -> button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_start))
                        solutionList.last() -> button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_end))
                        else -> button.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))
                    }
                }
            }

            State.INGAME -> {
                val selectedList = model.getSelected()

                // Reset all button states
                buttonGrid.forEach { it.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button)) }

                // Highlight selected buttons only
                for (i in selectedList) {
                    buttonGrid[i - 1].setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_pressed))
                }
            }
        }
    }*/

    fun draw(){
        //If state is pregame

        for (i in 1..42) {
            val buttonId = "btnRound$i"
            val resId = resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button))
        }

        if(state==State.PREGAME) {
            if (solutionList.isEmpty()){
                solutionList = model.findPathWithMid(1, (2..41).random(), 42)
            }
            else{
                solutionList = model.getNextSolution()
            }

            Log.d("points", "Queue size: ${model.solutionQueue.size}")
            Log.d("points", "New game with new solution: $solutionList")

            for(i:Int in solutionList) {
                val buttonId = "btnRound$i"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<AppCompatButton>(resId)
                when (i) {
                    solutionList[0] -> {
                        button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button_start))
                    }
                    solutionList[solutionList.lastIndex] -> {
                        button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button_end))
                    }
                    else -> {
                        button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button_pressed))
                    }
                }
                }
        }

        else if(state==State.INGAME){
            val selectedList: ArrayList<Int> = model.getSelected()

            for(i:Int in 1..42){
                val buttonId = "btnRound$i"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<AppCompatButton>(resId)

                if(selectedList.contains(i)) {
                    button.setBackgroundDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.round_button_pressed
                        )
                    )
                    Log.d("points", "touched button $button")
                }
                else
                    button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button))
            }
        }
    }

    private fun equal(solution: List<Int>, selected: ArrayList<Int>): Boolean{
        return selected.hashCode() == solution.hashCode()
    }

    fun compareList(v: View) {
        val submitButton = findViewById<AppCompatButton>(R.id.button2)
        submitButton.setOnClickListener {
            val solution = solutionList
            Log.d("points", "solution: $solution")
            val selected = model.getSelected()
            Log.d("points", "selected: $selected")
                if (!equal(solutionList,selected)) {
                    model.decAttempts()
                    if(model.getAttempts() == 0){
                        finish()
                    }
                    val popup = Snackbar.make(v, "${model.getAttempts()} attempts remaining.", 1000)
                    popup.show()
                    Log.w("Not in list", "Not in list")
                    Log.w("solution", solution.toString())
                    Log.w("selected", selected.toString())
                    //state = State.PREGAME
                    //draw()
                } else {
                    Log.w("All good", "All good")
                    Log.w("solution", solution.toString())
                    Log.w("selected", selected.toString())
                    val builder = AlertDialog.Builder(v.context)
                    builder.setMessage("You won")
                        .setPositiveButton("Try again?") { dialog, id ->
                            selected.clear()
                            refresh(v)
                        }
                    // Create the AlertDialog object and return it.
                    builder.create()
                    builder.show()
                }

        }
    }
}