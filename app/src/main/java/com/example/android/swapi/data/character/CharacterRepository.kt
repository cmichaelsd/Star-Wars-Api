package com.example.android.swapi.data.character

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
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
 * @date 10/23/20
 *
 */
class CharacterRepository(val app: Application): NetworkOperationsImpl() {

    // create mutable data for this character model
    // this will bind the view-model and the character repo
    // to keep track of any data change which happen for this model
    val characterData = MutableLiveData<Character>()

//    // init the type for the characters list
//    private val listType = Types.newParameterizedType(
//        List::class.java,
//        Character::class.java
//    )

    init {
        // create a co routine which uses dispatchers.io
        // dispatchers.io runs a task on a background thread
        // (dispatchers.main means run a task in foreground thread)
        CoroutineScope(Dispatchers.IO).launch {

            // when this repository is created get the character data which
            // can then be accessed through the view-model
            callWebService()
        }
    }

    // declare a function to get data from the web
    // declare this function as a worker thread
    // android must use webservice on background thread not a ui thread
    @WorkerThread
    private suspend fun callWebService() {
        val networkAvailability = networkAvailable(app)
        if (networkAvailability) {
            val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

            // establish Im using retrofit
            // declare my base url for my remote data
            // use moshi converter to parse json I will get
            val retrofit = Retrofit.Builder()
                    .baseUrl(WEB_SERVICE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()

            // declare the service I want to use with this retrofit pattern I defined above
            val service = retrofit.create(CharacterService::class.java)
            val serviceData = service.getCharacterData(1).body()

//            Log.i(LOG_TAG, serviceData.toString())


            // I set the fetched data to character repos characterData variable
            // I can not use characterData.value = ... because that is used on UI thread
            // postValue is the background thread version of this
            // build a list of characters and then pass to the character list variable above
//            characterData.postValue(serviceData)
        }
    }

//    private fun getCharacterLocalData() {
//        val text = FileHelper.getTextFromResources(app, R.raw.characters_data)
//        // init moshi json parser
//        val moshi = Moshi.Builder()
//            .addLast(KotlinJsonAdapterFactory())
//            .build()
//
//        // for parsing this character json use the predefined list type
//        // moshi will now understand what to do with the json text it is parsing
//        val adapter: JsonAdapter<List<Character>> = moshi.adapter(listType)
//
//        // moshi will now accept the characters_data.json as text
//        // which will be parsed and formatted as the associated list type
//        characterData.value = adapter.fromJson(text) ?: emptyList()
//    }
}