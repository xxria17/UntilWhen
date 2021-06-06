package com.dhxxn.untilwhenaos.network

import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.model.User
import retrofit2.Call
import retrofit2.http.*

interface API {
    //로그인
    @POST("user/login")
    fun getLoginResponse(@Body user : Map<String, String>): Call<String>
    //회원가입
    @POST("user/")
    fun getJoinResponse(@Body user: Map<String, String>): Call<User>

    // 게시글 조회
    @GET("dday/")
    fun getDdayAll(@Header("X-AUTH-TOKEN") token : String): Call<List<Dday>>

    //게시글 작성
    @POST("dday/")
    fun addDday(@Header("X-AUTH-TOKEN") token: String, @Body dday: Dday): Call<Dday>
}