package com.example.android.swapi.data.character

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 11/04/20
 *
 */
//@Database(entities = [Character::class], version = 1, exportSchema = false)
//abstract class CharacterDatabase: RoomDatabase() {
//
//    abstract fun characterDao(): CharacterDao
//
//    companion object {
//        // The volatile tag here means this can be accessed by more than one thread at a time
//        @Volatile
//        private var INSTANCE: CharacterDatabase? = null
//
//        fun getDatabase(context: Context): CharacterDatabase {
//            if (INSTANCE == null) {
//                synchronized(this) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        CharacterDatabase::class.java,
//                        "characters.db"
//                    ).build()
//                }
//            }
//
//            return INSTANCE!!
//        }
//
//    }
//}