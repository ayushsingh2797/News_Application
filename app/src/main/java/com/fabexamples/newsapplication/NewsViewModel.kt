package com.fabexamples.newsapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {

    var newsArticlesLiveData :MutableLiveData<NewsModel>? = null
    var newsModelRepo:NewsModelRepo = NewsModelRepo.instance

    fun fetchNewsArticle(){
        newsArticlesLiveData = newsModelRepo.fetchNewsArticleFromAPI()
    }
}