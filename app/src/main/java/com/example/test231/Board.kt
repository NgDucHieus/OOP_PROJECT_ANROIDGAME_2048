package com.example.test231

open class Board {
    private val size = 4
    val board: Array<IntArray> = Array(size) { IntArray(size) }
    init {
        initializeBoard()
    }

    private fun initializeBoard(): Array<IntArray> {
        for (i in 0 until size) {
            for (j in 0 until size) {
                board[i][j] = 0
            }
        }
        val game = MainGame()
        game.addRandomNumber(board)
        return board
    }

    // Other methods for manipulating the board
}
