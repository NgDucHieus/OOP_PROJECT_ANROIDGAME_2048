package com.example.test231

open class Board {
    var currentstate:Array<IntArray> = Array(4) { IntArray(4) }
    val board: Array<IntArray> = Array(4) { IntArray(4) }
    init {
        currentstate = board
        initializeBoard()
    }

    private fun initializeBoard(): Array<IntArray> {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                board[i][j] = 0
            }
        }
        val game = MainGame()
        game.addRandomNumber(board)
        return board
    }

    // Other methods for manipulating the board
}
