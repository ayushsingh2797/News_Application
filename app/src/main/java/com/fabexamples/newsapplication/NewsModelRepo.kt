package com.fabexamples.newsapplication

import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsModelRepo {

    private val BASE_URL = "https://api.npoint.io/"
    private var newsModelList = MutableLiveData<NewsModel>()
    var  loginModel : LoginModel? = null
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiInterface: APIInterface = retrofit.create(APIInterface::class.java)


    companion object NewsModelRepoSingleton{
    val instance = NewsModelRepo()
    }

    fun fetchNewsArticleFromAPI() : MutableLiveData<NewsModel> {
        val call : Call<NewsModel> = apiInterface.getAllArticles()
        call.enqueue(object : Callback<NewsModel?> {
            override fun onResponse(call: Call<NewsModel?>, response: Response<NewsModel?>) {
               if(response.body() != null) {
                   val data : NewsModel = response.body() as NewsModel
                   newsModelList.value = data
               }
            }
            override fun onFailure(call: Call<NewsModel?>, t: Throwable) {
                newsModelList.value = null
            }
        })
        return newsModelList

    }

    fun getLoginDetails() :  LoginModel? {

        val call : Call<LoginModel> = apiInterface.getLoginDetails()
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel?>, response: Response<LoginModel?>) {
                if(response.body() != null) {
                    val data : LoginModel = response.body() as LoginModel
                    loginModel = data
                }
            }
            override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
                loginModel = null
            }
        })
        return loginModel
    }
}