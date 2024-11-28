package ca.mobiledev.remind

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.GridLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.iterator

class Game : BaseActivity() {

    private val model: MazeModel = MazeModel()
    private var solutionList: List<Int> = emptyList()
    private lateinit var buttonGrid: List<AppCompatButton>
    private lateinit var lineView: LineView
    private lateinit var gridLayout: GridLayout

    private lateinit var refreshButton: AppCompatButton
    private lateinit var submitButton: AppCompatButton

    enum class State {
        PREGAME, INGAME
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

        refreshButton.setOnClickListener{
            refresh()
        }

        submitButton.setOnClickListener{
            //handle submit
        }

        var currentButton = -1
        gridLayout.setOnTouchListener { view, motionEvent ->
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
                solutionList = model.getSolution()
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

    fun refresh() {
        clear()
        Log.d("points", "Refreshed game")
        state = State.PREGAME
        Log.d("SolutionSize", "$solutionList")
        draw()
        state = State.INGAME
    }

    fun draw() {
        clear() //clears buttons
        if (state == State.PREGAME) {
            Log.d("points", "New game with new solution: $solutionList")
            for (i: Int in solutionList) {
                val buttonId = "btnRound$i"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<AppCompatButton>(resId)
                try {
                    when (i) {
                        solutionList[0] -> {
                            button.setBackgroundDrawable(
                                AppCompatResources.getDrawable(
                                    this,
                                    R.drawable.round_button_start
                                )
                            )
                        }
                        solutionList[solutionList.lastIndex] -> {
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
            lineView.setPath(solutionList)
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
        }
    }

    inner class LineView(context: Context, private val gridLayout: GridLayout) : View(context) {
        private val paint = Paint().apply {
            color = Color.RED
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
        private val path = Path()

        fun setPath(buttonList: List<Int>) {
            path.reset()
            val location = IntArray(2)
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
    }
}