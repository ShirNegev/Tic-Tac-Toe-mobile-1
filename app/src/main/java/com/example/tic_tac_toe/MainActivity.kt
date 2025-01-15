package com.example.tic_tac_toe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var isGameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttons = listOf(
            findViewById<Button>(R.id.button1), findViewById(R.id.button2),
            findViewById(R.id.button3), findViewById(R.id.button4),
            findViewById(R.id.button5), findViewById(R.id.button6),
            findViewById(R.id.button7), findViewById(R.id.button8),
            findViewById(R.id.button9)
        )

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)
        val tvTurnIndicator = findViewById<TextView>(R.id.tvTurnIndicator)

        // Assign click listeners to buttons
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (isGameActive && button.text.isEmpty()) {
                    val row = index / 3
                    val col = index % 3
                    button.text = currentPlayer
                    board[row][col] = currentPlayer

                    if (checkWin()) {
                        tvResult.text = "Player $currentPlayer Wins!"
                        isGameActive = false
                        btnPlayAgain.visibility = View.VISIBLE
                        tvTurnIndicator.text = ""
                    } else if (isBoardFull()) {
                        tvResult.text = "It's a Draw!"
                        isGameActive = false
                        btnPlayAgain.visibility = View.VISIBLE
                        tvTurnIndicator.text = ""
                    } else {
                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                        tvTurnIndicator.text = "$currentPlayer Turn"
                    }
                }
            }
        }

        // Play again button logic
        btnPlayAgain.setOnClickListener {
            resetGame(buttons, tvResult)
        }
    }

    private fun checkWin(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer)
                return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
                return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
            return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) return false
            }
        }
        return true
    }

    private fun resetGame(buttons: List<Button>, tvResult: TextView) {
        currentPlayer = "X"
        isGameActive = true
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null
            }
        }
        buttons.forEach { it.text = "" }
        tvResult.text = "Tic Tac Toe"
        findViewById<Button>(R.id.btnPlayAgain).visibility = View.GONE
    }
}