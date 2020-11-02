package com.example.android.swapi.data.swapi

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.android.swapi.LOG_TAG
import com.example.android.swapi.WEB_SERVICE_URL
import com.example.android.swapi.data.network.NetworkOperationsImpl
import com.example.android.swapi.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
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

    private val service: SwapiResponseService
    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val swapiCharactersData = MutableLiveData<SwapiResponse>()

    init {
        service = createService()
        refreshData()
    }

    // tag this as a worker thread
    // this logic will be ran on a background thread
    @WorkerThread
    private suspend fun callWebService() {
        if (networkAvailable(app)) {
            Log.i(LOG_TAG, "Calling web service")
            val data = service.getCharactersData().body()
            swapiCharactersData.postValue(data)

            if (data != null) {
                saveDataToCache(data)
            }
        }
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    private fun saveDataToCache(swapiResponse: SwapiResponse) {
        val adapter: JsonAdapter<SwapiResponse> = moshi.adapter(SwapiResponse::class.java)
        val json = adapter.toJson(swapiResponse)
        FileHelper.saveTextToFile(app, json)
    }

    private fun createService(): SwapiResponseService {
        val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        return retrofit.create(SwapiResponseService::class.java)
    }
}