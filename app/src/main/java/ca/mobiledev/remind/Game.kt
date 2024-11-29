package ca.mobiledev.remind

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.ViewModelProvider
import android.content.Intent

class Game : BaseActivity() {

    private val model: MazeModel = MazeModel()
    private lateinit var lineView: LineView
    private lateinit var gridLayout: GridLayout

    private lateinit var refreshButton: AppCompatButton
    private lateinit var submitButton: AppCompatButton

    private lateinit var levels: TextView
    private lateinit var itemViewModel: PathScoreViewModel
    private var startTime: Long = 0

    enum class State {
        PREGAME, INGAME , ENDGAME
    }
    private var state: State = State.PREGAME

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_game, findViewById(R.id.content_frame))
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)

        gridLayout = findViewById(R.id.gameGrid)
        lineView = LineView(this, gridLayout)
        frameLayout.addView(lineView)

        refreshButton = findViewById(R.id.buttonRefresh)
        submitButton = findViewById(R.id.buttonSubmit)

        submitButton.setOnClickListener{
            submit()
        }

        refreshButton.setOnClickListener{
            refresh()
        }

        levels = findViewById(R.id.level)


        var currentButton = -1
        gridLayout.setOnTouchListener { _, motionEvent ->
            val buttonNo = getChildAtXY(motionEvent.x, motionEvent.y, gridLayout)

            if (buttonNo != -1) {
                if (buttonNo != currentButton) {
                    Log.d("Found Button", "$buttonNo")
                    model.addPoint(buttonNo)
                    draw()
                    currentButton = buttonNo
                }
            }
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                currentButton = -1
            }
            true
        }

        for (i in 1..42) {
            val buttonId = "btnRound$i"
            val resId = resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            button.isClickable = false
        }

        gridLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //solutionList = model.getSolution()
                draw()
                state = State.INGAME
                gridLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun clear() {
        for (i in 1..42) {
            val buttonId = "btnRound$i"
            val resId = resources.getIdentifier(buttonId, "id", packageName)
            val button = findViewById<AppCompatButton>(resId)
            button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button))
        }
        lineView.clearPaths()
    }

    private fun getChildAtXY(x: Float, y: Float, gridLayout: GridLayout): Int {
        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            if (x > view.left && x < view.right && y > view.top && y < view.bottom) {
                return i + 1
            }
        }
        return -1
    }

    private fun restart(){
        state = State.PREGAME
        model.restart()
        //model.getSolution()
        draw()
        state = State.INGAME
    }

    private fun refresh() {
        clear()
        model.clearSelected()
        Log.d("points", "Refreshed game")
        state = State.PREGAME
        if(model.attemptsLeft()){
            model.getNewPath()
            draw()
            state = State.INGAME
        }
        else{
            state = State.ENDGAME
            draw()
        }

    }

    fun draw() {
        clear() //clears buttons
        val string = "Level: ${model.getLevel()}"
        levels.text = string
        if(state == State.ENDGAME){
            val builder = AlertDialog.Builder(submitButton.context)
            builder.setMessage("You reached level ${model.getLevel()}")

                .setPositiveButton("Try Again?") { _, _ ->
                    // START THE GAME!
                    restart()
                }
                .setNegativeButton("Quit") { _, _ ->
                    // User cancelled the dialog.
                    finish()
                }
            // Create the AlertDialog object and return it.
            builder.create()
            builder.show()



        }
        if (state == State.PREGAME) {
            Log.d("points", "New game with new solution: ${model.getSolution()}")
            for (i: Int in model.getSolution()) {
                val buttonId = "btnRound$i"
                val resId = resources.getIdentifier(buttonId, "id", packageName)

                val button = findViewById<AppCompatButton>(resId)
                try {
                    when (i) {
                        model.getSolution()[0] -> {
                            button.setBackgroundDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.round_button_start
                                )
                            )
                        }
                        model.getSolution()[model.getSolution().lastIndex] -> {
                            button.setBackgroundDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.round_button_end
                                )
                            )
                        }
                        else -> {
                            button.setBackgroundDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.round_button_pressed
                                )
                            )
                        }
                    }
                } catch (exception: NullPointerException) {
                    Log.e("NullOfWhat", "$i")
                }
            }
            lineView.setPath(model.getSolution())
            Log.d("Drawin Line", "Drew solution list")
        } else if (state == State.INGAME) {
            val selectedList: ArrayList<Int> = model.getSelected()
            for (i: Int in 1..42) {
                val buttonId = "btnRound$i"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<AppCompatButton>(resId)
                if (selectedList.contains(i)) {
                    button.setBackgroundDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.round_button_pressed
                        )
                    )
                    Log.d("points", "touched button $button")
                } else {
                    button.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.round_button))
                }
            }
            lineView.setPath(selectedList)
            Log.d("Drawin Line", "Drew selected list")
        }
    }

    private fun submit() {
        if(!model.compare()){
            val popup = Snackbar.make(submitButton,"Try Again", 1000)
            popup.show()
            Log.w("Not in list", "Not in list")
            model.decAttempts()
            refresh()
        }
        else{
            Log.w("All good", "All good")
            model.incLevel()
            refresh()
        }
    }

    inner class LineView(context: Context, private val gridLayout: GridLayout) : View(context) {
        private val paint = Paint().apply {
            color = getColor(R.color.blue)
            strokeWidth = 10f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            pathEffect = CornerPathEffect(15.0f)
        }

        private val path = Path()

        fun setPath(buttonList: List<Int>) {
            path.reset()
            val xOffset = gridLayout.x
            val yOffset = gridLayout.y


            if (buttonList.isNotEmpty()) {
            val firstButton = gridLayout.getChildAt(buttonList[0] - 1)
            path.moveTo(
                 firstButton.x + firstButton.width / 2 + xOffset,
                 firstButton.y + firstButton.height / 2 + yOffset
            )
            for (i in 1 until buttonList.size) {
                val button = gridLayout.getChildAt(buttonList[i] - 1)
                path.lineTo(
                     button.x + button.width / 2 + xOffset,
                     button.y + button.height / 2 + yOffset
                   )

                val arr= IntArray(2)
                button.getLocationOnScreen(arr)
                Log.d("Drawin Line", "at ${arr[0]} , ${arr[1]}")
                }
            }
            invalidate()
        }

        fun clearPaths() {
            path.reset()
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawPath(path, paint)
        }
        private fun saveScore(){
            val endTime = System.currentTimeMillis()
            val timeTaken = endTime - startTime
            val currentDate = java.time.LocalDateTime.now().toString()
            val levelReached = model.getLevel()
            val score = calculateScore(levelReached, timeTaken)

            itemViewModel.insert(currentDate, score, timeTaken, levelReached)
        }

        private fun calculateScore(level: Int, timeTaken: Long): Int {
            //base socre is level * 1000
            val baseScore = level * 1000
            val timePenalty = (timeTaken/1000).toInt().coerceAtMost(baseScore/2)
            return baseScore - timePenalty
        }


    }
}