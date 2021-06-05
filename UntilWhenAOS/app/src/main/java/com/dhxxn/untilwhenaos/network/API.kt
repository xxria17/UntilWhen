package com.dhxxn.untilwhenaos.network

import com.dhxxn.untilwhenaos.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @POST("user/login")
    fun getLoginResponse(@Body user : Map<String, String>): Call<String>

    @POST("user/")
    fun getJoinResponse(@Body user: Map<String, String>): Call<User>
}