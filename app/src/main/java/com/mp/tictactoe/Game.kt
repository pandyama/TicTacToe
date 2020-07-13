package com.mp.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.app.AlertDialog

class Game : AppCompatActivity() {


    var activePlayer = 1
    var playerList: MutableList<Int> = ArrayList()
    var androidList: MutableList<Int> = ArrayList()

    var playerScore = 0
    var androidScore = 0

    var dbManager = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var dbManager = DatabaseHelper(this)

        playerCount.text = dbManager.getScore("HomePlayer").toString()
        androidCount.text = dbManager.getScore("AndroidPlayer").toString()

    }

    fun boardClicked(v: View) {
        val clickedButton = v as Button
        var cellId = 0

        when (clickedButton.id) {
            R.id.button1 -> cellId = 1
            R.id.button2 -> cellId = 2
            R.id.button3 -> cellId = 3
            R.id.button4 -> cellId = 4
            R.id.button5 -> cellId = 5
            R.id.button6 -> cellId = 6
            R.id.button7 -> cellId = 7
            R.id.button8 -> cellId = 8
            R.id.button9 -> cellId = 9
        }
        playGame(cellId)
    }

    fun playGame(cellId: Int) {
        if (playerList.size + androidList.size == 9) {
            checkWinner()
        } else if (activePlayer == 1 && !(playerList.contains(cellId)) && !(androidList.contains(
                cellId
            ))
        ) {
            when (cellId) {
                1 -> {
                    button1.setText("X")
                    playerList.add(1)
                }
                2 -> {
                    button2.setText("X")
                    playerList.add(2)
                }
                3 -> {
                    button3.setText("X")
                    playerList.add(3)
                }
                4 -> {
                    button4.setText("X")
                    playerList.add(4)
                }
                5 -> {
                    button5.setText("X")
                    playerList.add(5)
                }
                6 -> {
                    button6.setText("X")
                    playerList.add(6)
                }
                7 -> {
                    button7.setText("X")
                    playerList.add(7)
                }
                8 -> {
                    button8.setText("X")
                    playerList.add(8)
                }
                9 -> {
                    button9.setText("X")
                    playerList.add(9)
                }
            }
            checkWinner()
            activePlayer = 2
            autoPlay()

        } else if (activePlayer == 2) {
            autoPlay()
            checkWinner()
        }

    }

    fun checkWinner() {
        var winner = -1

        if (playerList.contains(1) && playerList.contains(2) && playerList.contains(3)) {
            winner = 1
        }
        if (androidList.contains(1) && androidList.contains(2) && androidList.contains(3)) {
            winner = 2
        }

        if (playerList.contains(4) && playerList.contains(5) && playerList.contains(6)) {
            winner = 1
        }
        if (androidList.contains(4) && androidList.contains(5) && androidList.contains(6)) {
            winner = 2
        }

        if (playerList.contains(7) && playerList.contains(8) && playerList.contains(9)) {
            winner = 1
        }
        if (androidList.contains(7) && androidList.contains(8) && androidList.contains(9)) {
            winner = 2
        }

        if (playerList.contains(1) && playerList.contains(4) && playerList.contains(7)) {
            winner = 1
        }
        if (androidList.contains(1) && androidList.contains(4) && androidList.contains(7)) {
            winner = 2
        }

        if (playerList.contains(2) && playerList.contains(5) && playerList.contains(8)) {
            winner = 1
        }
        if (androidList.contains(2) && androidList.contains(5) && androidList.contains(8)) {
            winner = 2
        }

        if (playerList.contains(3) && playerList.contains(6) && playerList.contains(9)) {
            winner = 1
        }
        if (androidList.contains(3) && androidList.contains(6) && androidList.contains(9)) {
            winner = 2
        }

        if (playerList.contains(1) && playerList.contains(5) && playerList.contains(9)) {
            winner = 1
        }
        if (androidList.contains(1) && androidList.contains(5) && androidList.contains(9)) {
            winner = 2
        }

        if (playerList.contains(3) && playerList.contains(5) && playerList.contains(7)) {
            winner = 1
        }
        if (androidList.contains(3) && androidList.contains(5) && androidList.contains(7)) {
            winner = 2
        }

        if (winner == 1) {
            Toast.makeText(this, "You win", Toast.LENGTH_LONG).show()
            playerScore++
            callDb("HomePlayer")
            dialog()
            reset()

        } else if (winner == 2) {
            Toast.makeText(this, "Sorry, Android won", Toast.LENGTH_LONG).show()
            androidScore++
            callDb("AndroidPlayer")
            dialog()
            reset()
        } else if (androidList.size + playerList.size == 9) {
            Toast.makeText(this, "Nobody won", Toast.LENGTH_LONG).show()
            dialog()
            reset()
        }
    }

    fun callDb(player: String){
        var res = dbManager.writableDatabase.rawQuery(
            "SELECT COUNT(*) FROM Scores WHERE Player='${player}'", null)
        if(res.moveToFirst()){
            if(res.getInt(0) == 0){
                if(player == "HomePlayer") {
                    dbManager.insert("${player}", playerScore)
                }
                else{
                    dbManager.insert("${player}", androidScore)
                }
                dbManager.getAll()
            }
            else{
                var current = dbManager.getScore("${player}")
                dbManager.update("${player}", current+1)
                dbManager.getAll()
            }
        }
    }

    fun dialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Play Again?")
        builder.setPositiveButton("Yes") { dialog, which ->
            Toast.makeText(applicationContext, "Play", Toast.LENGTH_SHORT).show()
            reset()
        }
        builder.setNegativeButton("No") { dialog, which ->
            Toast.makeText(applicationContext, "Next Time", Toast.LENGTH_SHORT).show()
            reset()
        }
        builder.show()
    }

    fun resetScore(v: View){
        dbManager.writableDatabase.delete("Scores",null,null)
        playerCount.text = "0"
        androidCount.text = "0"
    }

    fun reset() {
        button1.text = ""
        button2.setText("")
        button3.setText("")
        button4.setText("")
        button5.setText("")
        button6.setText("")
        button7.setText("")
        button8.setText("")
        button9.setText("")
        androidList.clear()
        playerList.clear()
        playerCount.text = dbManager.getScore("HomePlayer").toString()
        androidCount.text = dbManager.getScore("AndroidPlayer").toString()
        activePlayer = 1
    }

    fun autoPlay() {
        var freeCell = ArrayList<Int>()
        var randomNum = Random()
        var randIndex = 0

        if (playerList.size + androidList.size == 9) {
            checkWinner()
        }
        for (cell in 1..9) {
            if (!(playerList.contains(cell)) && !(androidList.contains(cell))) {
                freeCell.add(cell)
                randIndex = randomNum.nextInt(freeCell.size)
            }
        }
        if (freeCell.size == 0) {
            reset()
        } else if (!(androidList.contains(freeCell[randIndex])) && !(playerList.contains(freeCell[randIndex]))) {
            when (freeCell[randIndex]) {
                1 -> {
                    button1.text = "O"
                    androidList.add(1)
                }
                2 -> {
                    button2.text = "O"
                    androidList.add(2)
                }
                3 -> {
                    button3.text = "O"
                    androidList.add(3)
                }
                4 -> {
                    button4.text = "O"
                    androidList.add(4)
                }
                5 -> {
                    button5.text = "O"
                    androidList.add(5)
                }
                6 -> {
                    button6.text = "O"
                    androidList.add(6)
                }
                7 -> {
                    button7.text = "O"
                    androidList.add(7)
                }
                8 -> {
                    button8.text = "O"
                    androidList.add(8)
                }
                9 -> {
                    button9.text = "O"
                    androidList.add(9)
                }
            }
            checkWinner()
            activePlayer = 1
        }
    }
}
