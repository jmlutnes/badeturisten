package no.uio.ifi.in2000.team37.badeturisten.data.favourite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavouriteDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //lager/initierer database
    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE ${FavouriteContract.FavoriteEntry.TABLE_NAME} (" +
                "${FavouriteContract.FavoriteEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${FavouriteContract.FavoriteEntry.COLUMN_BEACH} BEACH NOT NULL" +
                // Define other columns here
                ");"
        db.execSQL(SQL_CREATE_FAVORITES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${FavouriteContract.FavoriteEntry.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "favorites.db"
        const val DATABASE_VERSION = 1
    }
}