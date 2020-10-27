package com.example.android.swapi.data.network

import android.content.Context

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
interface NetworkOperations {
    fun networkAvailable(context: Context): Boolean
}