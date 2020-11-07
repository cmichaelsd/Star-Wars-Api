package com.example.android.swapi.data.swapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 11/07/20
 *
 */
@Database(entities = [SwapiResponse::class], version = 1, exportSchema = false)
abstract class SwapiResponseDatabase: RoomDatabase() {

    abstract fun swapiResponseDao(): SwapiResponseDao

    companion object {
        @Volatile
        private var INSTANCE: SwapiResponseDatabase? = null

        fun getDatabase(context: Context): SwapiResponseDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SwapiResponseDatabase::class.java,
                        "swapi_response.db"
                    ).build()
                }
            }

            return INSTANCE!!
        }
    }
}