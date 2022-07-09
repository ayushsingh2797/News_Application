package com.fabexamples.newsapplication

import retrofit2.Call
import retrofit2.http.GET


interface APIInterface {

    @GET("7c27fa874f0a4d46e4d4")
    fun getAllArticles() : Call<NewsModel>

    @GET("0774724810730d4ee184")
    fun getLoginDetails() : Call<LoginModel>

}