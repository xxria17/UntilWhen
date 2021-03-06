package com.dhxxn.untilwhenaos.network

import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.model.User
import retrofit2.Call
import retrofit2.http.*

interface API {

    // 사용자 이름으로 아이디 받아오기
    @GET("user/{name}")
    fun getUserId(@Header("token") token: String, @Path("name") name: String): Call<User>

    //로그인
    @POST("user/login")
    fun getLoginResponse(@Body user : Map<String, String>): Call<String>

    //회원가입
    @POST("user/")
    fun getJoinResponse(@Body user: Map<String, String>): Call<User>

    //회원탈퇴
    @DELETE("user/{id}")
    fun deleteUser(@Header("X-AUTH-TOKEN") token: String, @Path("id") id: Int): Call<Void>

    // 게시글 조회
    @GET("dday/")
    fun getDdayAll(@Header("X-AUTH-TOKEN") token : String): Call<List<Dday>>

    //게시글 작성
    @POST("dday/")
    fun addDday(@Header("X-AUTH-TOKEN") token: String, @Body dday: Dday): Call<Dday>

    //게시글 수정
    @PUT("dday/{id}")
    fun updateDday(@Header("X-AUTH-TOKEN") token: String, @Path("id") id : Int, @Body day : Dday): Call<Dday>

    //게시글 삭제
    @DELETE("dday/{id}")
    fun deleteDday(@Header("X-AUTH-TOKEN") token: String, @Path("id") id : Int): Call<Void>

}