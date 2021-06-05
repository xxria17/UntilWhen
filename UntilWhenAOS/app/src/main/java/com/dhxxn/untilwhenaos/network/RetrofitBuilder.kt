package com.dhxxn.untilwhenaos.network

import com.dhxxn.untilwhenaos.R
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api: API

    val gson = GsonBuilder()
            .setLenient()
            .create()

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        api = retrofit.create(API::class.java)
    }
}