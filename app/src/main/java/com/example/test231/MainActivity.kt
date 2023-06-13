package com.example.test231

import GameOverDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.atan2

class MainActivity : AppCompatActivity() {
    private lateinit var context:Context
    private lateinit var matrix: Array<Array<TextView>> // 2d matrix
    private var x1:Double = 0.0
    private var y1 :Double = 0.0
    private var x2: Double = 0.0
    private var y2:Double =0.0
    private lateinit var tvScore:TextView
    private lateinit var tvBest: TextView
    private lateinit var game: MainGame
    private lateinit var board: Board
    private lateinit var customFont:Typeface
    private lateinit var buttonRestart: Button
    private lateinit var best:SharedPreferences
    private lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //dialog declare
        dialog = Dialog(this)
        //set transparent

        //save high score
        context = applicationContext
        best = context.getSharedPreferences("High_score",Context.MODE_PRIVATE)
        //
// text font
        customFont = Typeface.createFromAsset(assets,"ClearSans-Bold.ttf")
        tvScore =findViewById(R.id.score)
        tvBest =findViewById(R.id.best)

        tvBest.apply {
            typeface = customFont
        }
        tvScore.apply {
            typeface = customFont
        }
//
//

        buttonRestart = findViewById(R.id.restart)

        val row0Layout: LinearLayout = findViewById(R.id.row0)
        val row1Layout: LinearLayout = findViewById(R.id.row1)
        val row2Layout: LinearLayout = findViewById(R.id.row2)
        val row3Layout: LinearLayout = findViewById(R.id.row3)
        matrix = Array(4) { Array(4) { TextView(this) } }

        for (i in 0 until row0Layout.childCount) {
            val textView: TextView = row0Layout.getChildAt(i) as TextView
            matrix[0][i] = textView

        }
        for (i in 0 until row1Layout.childCount) {
            val textView: TextView = row1Layout.getChildAt(i)
                    as TextView
            matrix[1][i] = textView
        }
        for (i in 0 until row2Layout.childCount) {
            val textView: TextView = row2Layout.getChildAt(i) as TextView
            matrix[2][i] = textView
        }
        for (i in 0 until row3Layout.childCount) {
            val textView: TextView = row3Layout.getChildAt(i) as TextView
            matrix[3][i] = textView
        }

        // Initialize the game and board
        setUpNewGame()


    }


    private fun setUpNewGame()
    {
        game = MainGame()
        board = Board()
        updateUI()
        updatescore()
        buttonRestart.setOnClickListener {
            setUpNewGame()
        }

    }
    private fun updateUI() {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val value = board.board[i][j]

                matrix[i][j].apply {
                    if (value != 0) {
                        text = value.toString()
                        typeface = customFont //set Font for text
                    } else {
                        text = ""
                    }
                    textSize = 40f
                }
                val textview =matrix [i][j]
                paintCell(textview,value)
            }
        }
    }
    private fun paintCell(textView: TextView, value: Int) {
        val bgCol = bgCol(value)
        val txCol = txCol(value)
        textView.setTextColor(txCol)
        textView.backgroundTintList = ColorStateList.valueOf(bgCol)
        textView.setBackgroundResource(R.drawable.custom_cell)

    }
    private fun txCol(value: Int):Int
    {
        var colorcode =resources.getColor(R.color.t_dark_text,null)
        if(value in 8 until  256)
        {
            colorcode=resources.getColor(R.color.t_light_text,null)
        }
        return colorcode
    }
    private fun bgCol(value: Int):Int {
        var bgCol = resources.getColor(R.color.btnColor,null) //bgCol
        when (value) {
            2 -> bgCol = resources.getColor(R.color.t2_bg,null)
            4 -> bgCol = resources.getColor(R.color.t4_bg,null)
            8 -> bgCol = resources.getColor(R.color.t8_bg,null )
            16 -> bgCol = resources.getColor(R.color.t16_bg ,null)
            32 -> bgCol = resources.getColor(R.color.t32_bg,null)
            64 -> bgCol = resources.getColor(R.color.t64_bg ,null)
            128 -> bgCol = resources.getColor(R.color.t128_bg,null )
            256 -> bgCol = resources.getColor(R.color.t256_bg ,null)
            512 -> bgCol = resources.getColor(R.color.t512_bg,null)
            1024 -> bgCol = resources.getColor(R.color.t1024_bg,null)
            2048 -> bgCol = resources.getColor(R.color.t2048_bg,null)
            else -> { } // won't happen
        }
        return bgCol
    }
    private fun updatescore()
    {
        val currentscore = game.score
        tvScore.text = currentscore.toString()

        val highscore = best.getInt("highscore",0)
        tvBest.text = highscore.toString()
        val editbest = best.edit()
        if(currentscore>highscore)
        {
            editbest.putInt("highscore",currentscore)
            editbest.apply()
            tvBest.text = currentscore.toString()

        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            // when the user first touches the screen we get x and y coordinates
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x.toDouble()
                y1 = event.y.toDouble()
            }
            // when the user releases the touch, we will have x and y coordinates
            MotionEvent.ACTION_UP -> {
                 x2 = event.x.toDouble()
                 y2 = event.y.toDouble()
                val minDistance = 100 // pixel
                if (x1 < x2 && x2 - x1 > minDistance) {   //swipe right
                    if(game.isGameOver(board.board))
                    {
                        showGameOverDialog2()
                    }
                    else if(y2>y1) //bottom right
                    {
                        if(calculateAngle(x1,y1,x2,y2) > 45)
                        {
                            game.swipeRight(board.board)
                            updateUI()
                            updatescore()
                        }
                        else
                        {
                            game.swipeDown(board.board)
                            updateUI()
                            updatescore()
                        }
                    }
                    else if( y2<y1) //top right
                    {
                        if(calculateAngle(x1,y2,x2,y1) > 45)
                        {
                            game.swipeUp(board.board)
                            updateUI()
                            updatescore()
                        }
                        else
                        {
                            game.swipeRight(board.board)
                            updateUI()
                            updatescore()
                        }
                    }
                    else{
                        game.swipeRight(board.board)
                         updateUI()
                        updatescore()
                    }

                }
                else if (x1 > x2 && x1 - x2 > minDistance) { //swipe left
                    if(game.isGameOver(board.board))
                    {
                        showGameOverDialog2()
                    }
                    else if(y1>y2) //top left
                    {
                        if(calculateAngle(x2,y2,x1,y1) > 45)
                        {
                            game.swipeUp(board.board)
                            updateUI()
                            updatescore()
                        }
                        else
                        {
                            game.swipeLeft(board.board)
                            updateUI()
                            updatescore()
                        }
                    }
                    else if(y2>y1) //bottom left
                    {
                        if(calculateAngle(x2,y1,x1,y2) > 45)
                        {
                            game.swipeDown(board.board)
                            updateUI()
                            updatescore()
                        }
                        else
                        {
                            game.swipeLeft(board.board)
                            updateUI()
                            updatescore()
                        }
                    }
                    else {
                        game.swipeLeft(board.board)
                        updateUI()
                        updatescore()
                    }
                }
                else if (y1 < y2 && y2 - y1 > minDistance) { //swipe down
                    if(game.isGameOver(board.board))
                    {
                        showGameOverDialog2()
                    }
                    game.swipeDown(board.board)
                    updateUI()
                    updatescore()
                }
                else if (y1 > y2 && y1 - y2 > minDistance) { //swipe up
                    if(game.isGameOver(board.board))
                    {
                        showGameOverDialog2()
                    }
                    game.swipeUp(board.board)
                    updateUI()
                    updatescore()

                }
            }
        }
        return super.onTouchEvent(event)
    }
    private fun showGameOverDialog2() {
        val dialog2 = GameOverDialog(this)
        {
            // Handle restart logic here
            setUpNewGame()
        }
        dialog2.show()

    }
    private fun calculateAngle(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        val angleRadians = atan2(y2 - y1, x2 - x1)
        val angleDegrees = Math.toDegrees(angleRadians)
        return angleDegrees
    }
}

