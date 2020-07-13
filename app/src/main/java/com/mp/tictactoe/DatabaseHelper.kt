package com.mp.tictactoe

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context):
    SQLiteOpenHelper(context, dbName, null, 1){

    val colID = "ID"
    val colScore = "Score"
    val colPlayer = "Player"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable " +
            "($colID INTEGER PRIMARY KEY, $colPlayer TEXT, $colScore INTEGER);"

    val context = context

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $dbName")
        onCreate(db)
    }

    fun insert(player: String, score: Int): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, player)
        contentValues.put(COL_2, score)
        return db.insert(dbTable, null, contentValues)
    }

    fun getAll(){
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM $dbTable", null)
        while(res.moveToNext()){
            println("Printing SQLite")
            println("----")
            println("${res.getColumnName(1)}")
            println("${res.getString(1)}")
            println("-----")
            println("${res.getColumnName(2)}")
            println("${res.getInt(2)}")
        }
    }

    fun dropTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $dbName")
    }

    fun update(player:String, score: Int){
        val db = this.writableDatabase
        db.execSQL(
            "UPDATE Scores SET Score='${score}' WHERE " +
                    "Player = '${player}'"
        )
    }

    fun getScore(player: String): Int{
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $COL_2 FROM $dbTable " +
                "WHERE $COL_1 = '$player'", null)
        var score = 0
        while(res.moveToNext()){
            score = res.getInt(0)
        }
        return score
    }

    companion object {
        val dbName = "TicTacToe"
        val dbTable = "Scores"
        val COL_0 = "ID"
        val COL_1 = "Player"
        val COL_2 = "Score"
    }



}


