package com.example.test231

import java.util.Random

open class MainGame {
    private val rand = Random()
    var score = 0
    private fun generateRandomNumber(): Int {
        return if (rand.nextFloat() < 0.9f) 2 else 4
    }

    // Function to add a random number to the game board
    fun addRandomNumber(board: Array<IntArray>) {
        val emptyCells = mutableListOf<Pair<Int, Int>>()

        // Find all empty cells on the board
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == 0) {
                    emptyCells.add(i to j)
                }
            }
        }

        // Select a random empty cell and assign a random number
        if (emptyCells.isNotEmpty()) {
            val randomCell = emptyCells.random()
            val randomNumber = generateRandomNumber()
            board[randomCell.first][randomCell.second] = randomNumber
        }
    }
    fun swipeLeft(board: Array<IntArray>) {
        var hasMove =false
        for (i in 0 until 4) {
            var merged = false
            var previousValue = 0
            var currentIndex = 0

            for (j in 0 until 4) {
                val currentValue = board[i][j]

                if (currentValue != 0) {
                    if (currentValue == previousValue && !merged) {
                        board[i][currentIndex - 1] = currentValue * 2
                        board[i][j] = 0
                        previousValue = 0
                        merged = true // each time merge score +
                        score += currentValue*2
                        hasMove =true
                    } else {
                        //sort board
                        board[i][currentIndex] = currentValue
                        if (j != currentIndex) {
                            board[i][j] = 0 
                            hasMove = true
                        }
                        previousValue = currentValue
                        currentIndex++
                        merged = false
                       
                    }
                }

            }

        }
        if(hasMove)
        {
            addRandomNumber(board)
        }
    }
    fun swipeRight(board: Array<IntArray>) {
        val size = board.size
        var hasMove = false
        for (i in 0 until size) {
            var merged = false
            var previousValue = 0
            var currentIndex = size - 1

            for (j in size - 1 downTo 0) {
                val currentValue = board[i][j]

                if (currentValue != 0) {
                    if (currentValue == previousValue && !merged) {
                        board[i][currentIndex + 1] = currentValue * 2
                        board[i][j] = 0
                        previousValue = 0
                        merged = true
                        score += currentValue*2
                        hasMove=true
                    } else {
                        board[i][currentIndex] = currentValue
                        if (j != currentIndex) {
                            board[i][j] = 0
                            hasMove =true
                        }
                        previousValue = currentValue
                        currentIndex--
                        merged = false
                    }
                }
            }
        }
        if(hasMove)
        {
            addRandomNumber(board)
        }
    }
    fun swipeUp(board: Array<IntArray>) {
        val size = board.size
        var hasMove = false
        for (j in 0 until size) {
            var merged = false
            var previousValue = 0
            var currentIndex = 0

            for (i in 0 until size) {
                val currentValue = board[i][j]

                if (currentValue != 0) {
                    if (currentValue == previousValue && !merged) {
                        board[currentIndex - 1][j] = currentValue * 2
                        board[i][j] = 0
                        previousValue = 0
                        merged = true
                        score += currentValue*2
                        hasMove =true
                    } else {
                        board[currentIndex][j] = currentValue
                        if (i != currentIndex) {
                            board[i][j] = 0
                            hasMove = true
                        }
                        previousValue = currentValue
                        currentIndex++
                        merged = false
                    }
                }
            }

        }
        if(hasMove)
        {
            addRandomNumber(board)
        }
    }
    fun swipeDown(board: Array<IntArray>) {
        val size = board.size
        var hasMove =false
        for (j in 0 until size) {
            var merged = false
            var previousValue = 0
            var currentIndex = size - 1

            for (i in size - 1 downTo 0) {
                val currentValue = board[i][j]

                if (currentValue != 0) {
                    if (currentValue == previousValue && !merged) {
                        board[currentIndex + 1][j] = currentValue * 2
                        board[i][j] = 0
                        previousValue = 0
                        merged = true
                        score += currentValue*2
                        hasMove =true
                    } else {
                        board[currentIndex][j] = currentValue
                        if (i != currentIndex) {
                            board[i][j] = 0
                            hasMove =true
                        }
                        previousValue = currentValue
                        currentIndex--
                        merged = false
                    }
                }
            }
        }
        if(hasMove)
        {
            addRandomNumber(board)
        }
    }
    fun isGameOver(board: Array<IntArray>): Boolean {
        val size = 4
        // Check if any cells are empty
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (board[i][j] == 0) {
                    return false
                }
            }
        }

        // Check for adjacent cells with the same value
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val currentValue = board[i][j]

                // Check left neighbor
                if (j > 0 && board[i][j - 1] == currentValue) {
                    return false
                }

                // Check right neighbor
                if (j < size - 1 && board[i][j + 1] == currentValue) {
                    return false
                }

                // Check top neighbor
                if (i > 0 && board[i - 1][j] == currentValue) {
                    return false
                }

                // Check bottom neighbor
                if (i < size - 1 && board[i + 1][j] == currentValue) {
                    return false
                }
            }
        }

        return true
    }
}