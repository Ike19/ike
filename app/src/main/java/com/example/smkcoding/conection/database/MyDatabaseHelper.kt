package com.example.smkcoding.conection.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.smkcoding.model.ResultsItem
import org.jetbrains.anko.db.*

class  MyDatabaseHelper (context: Context) :ManagedSQLiteOpenHelper
    (context, "database_movie.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            ResultsItem.TABLE_FAVORITE,true, ResultsItem.COLUMN_ID to INTEGER
            + PRIMARY_KEY + AUTOINCREMENT,
            ResultsItem.COLUMN_TITLE to TEXT,
            ResultsItem.COLUMN_POSTERPATH to TEXT,
            ResultsItem.COLUMN_RATING to REAL,
            ResultsItem.COLUMN_DESCRIPTION to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(ResultsItem.TABLE_FAVORITE, true)
        onCreate(db)
    }
}

val Context.database : MyDatabaseHelper
get() = MyDatabaseHelper(applicationContext)