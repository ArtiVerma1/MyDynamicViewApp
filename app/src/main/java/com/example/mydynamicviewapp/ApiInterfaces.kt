package com.example.mydynamicviewapp

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaces {

    @GET("magento/dev/marketplace_plat/pub/vendorapi/index/getProfileFields?hashkey=rNRccT7K6NVaZ6wgvsuLEUMow22IPYqp&vendor_id=1")
    fun ProfileFields():Call<JsonObject>
}