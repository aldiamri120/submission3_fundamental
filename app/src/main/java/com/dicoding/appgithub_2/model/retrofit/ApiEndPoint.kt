package com.dicoding.appgithub_2.model.retrofit

import com.dicoding.appgithub_2.model.UserArray
import com.dicoding.appgithub_2.model.UserDetail
import com.dicoding.appgithub_2.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserSearch(@Query("q") query: String): Call<UserArray>


    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetail>


    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>


    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>


}