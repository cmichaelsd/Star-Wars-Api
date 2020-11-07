package com.example.android.swapi.data.swapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 11/07/20
 *
 */
@Dao
interface SwapiResponseDao {
    @Query("SELECT * FROM swapi_response")
    fun getAll(): SwapiResponse

    @Insert
    suspend fun insertSwapiResponse(swapiResponse: SwapiResponse)

    @Insert
    suspend fun insertSwapiResponses(swapiResponses: List<SwapiResponse>)

    @Query("DELETE FROM swapi_response")
    suspend fun deleteAll()
}