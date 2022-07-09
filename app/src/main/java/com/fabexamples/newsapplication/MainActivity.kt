package com.fabexamples.newsapplication

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabexamples.newsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private val context : Context = applicationContext
    lateinit var newsRV :RecyclerView
    lateinit var mainActivityBinding: ActivityMainBinding
    lateinit var newsRVAdapter : NewsRVAdapter
    lateinit var newsViewModel: NewsViewModel
    var articleList : List<Article>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        newsRV = mainActivityBinding.rvNewsItems
        newsRV.layoutManager = LinearLayoutManager(this)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        fetchDataFromServer()
        observeLiveData()
    }

    private fun observeLiveData() {
       newsViewModel.newsArticlesLiveData?.observe(this, Observer<NewsModel> { t->
           if(t!= null){
               articleList = t.articles
               setAdapter(articleList!!)
           }
           else {
               mainActivityBinding.rvNewsItems.visibility = View.GONE
               mainActivityBinding.tvError.visibility = View.VISIBLE
           }
       })
    }

    private fun setAdapter(articleList: List<Article>) {
        newsRVAdapter = NewsRVAdapter(this,articleList)
        mainActivityBinding.rvNewsItems.adapter = newsRVAdapter
    }

    private fun fetchDataFromServer(){
        if(UtilityFunctions.isConnectingToInternet(applicationContext)){
            newsViewModel.fetchNewsArticle()
        }
    }
}