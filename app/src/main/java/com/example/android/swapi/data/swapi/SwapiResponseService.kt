package com.example.android.swapi.data.swapi

import retrofit2.Response
import retrofit2.http.GET

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
interface SwapiResponseService {
    @GET("/api/people")
    suspend fun getCharactersData(): Response<SwapiResponse>
}