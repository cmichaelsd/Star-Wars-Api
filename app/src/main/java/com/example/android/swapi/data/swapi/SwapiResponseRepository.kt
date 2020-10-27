package com.example.android.swapi.data.swapi

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.android.swapi.LOG_TAG
import com.example.android.swapi.WEB_SERVICE_URL
import com.example.android.swapi.data.network.NetworkOperationsImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
class SwapiResponseRepository(val app: Application): NetworkOperationsImpl() {

    val swapiCharactersData = MutableLiveData<SwapiResponse>()

    init {
        refreshData()
    }

    // tag this as a worker thread
    // this logic will be ran on a background thread
    @WorkerThread
    private suspend fun callWebService() {
        val networkAvailability = networkAvailable(app)

        if (networkAvailability) {
            Log.i(LOG_TAG, "Calling web service")
            val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(WEB_SERVICE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()

            val service = retrofit.create(SwapiResponseService::class.java)
            val serviceData = service.getCharactersData().body()

            swapiCharactersData.postValue(serviceData)
        }
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }
}