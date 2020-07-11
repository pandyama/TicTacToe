package com.mp.tictactoe

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper (context: Context):
    SQLiteOpenHelper(context, dbName, null, 1){

    val colID = "ID"
    val colScore = "Score"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable " +
            "($colID INTEGER PRIMARY KEY, $colScore INTEGER);"

    val context = context

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $dbName")
        onCreate(db)
    }

    fun insert(score: Int): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, score)

        return db.insert(dbTable, null, contentValues)

    }


    companion object {
        val dbName = "TicTacToe"
        val dbTable = "Scores"
        val COL_0 = "ID"
        val COL_1 = "Score"
    }



}


