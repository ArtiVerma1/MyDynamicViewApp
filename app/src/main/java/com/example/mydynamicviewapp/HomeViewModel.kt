package com.example.mydynamicviewapp

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeViewModel (val retrofit : Retrofit) : ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    private var userdata= MutableLiveData<JsonObject>().apply {  }
    val text: LiveData<String> = _text
    private var todoApi: ApiInterfaces? = null
//    private var dao: UserDao? = null
   // private var dataItem= MutableLiveData<DataItem>().apply {  }
    private val TAG = "MainActivity"
//    @Inject
//    lateinit var retrofit: Retrofit

    fun getUser() : LiveData<JsonObject>? {
        userdata = getServicesApiCall()
        return userdata
    }


    fun getServicesApiCall(): MutableLiveData<JsonObject> {

        todoApi = retrofit.create(ApiInterfaces::class.java)
        val response= todoApi?.ProfileFields()
        response?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val dataResponse = response.body().toString()

                userdata.postValue(response.body())

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
            }
        })
        return userdata
    }

}