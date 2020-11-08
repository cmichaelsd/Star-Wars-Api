package com.example.android.swapi.data.character

import com.example.android.swapi.data.swapi.SwapiCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/23/20
 *
 */
interface CharacterService {
    @GET("/api/people")
    suspend fun getCharactersData(@Query("page") page: Int): Response<SwapiCharacterResponse>
}