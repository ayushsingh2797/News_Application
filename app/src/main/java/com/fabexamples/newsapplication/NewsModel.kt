package com.fabexamples.newsapplication

data class NewsModel(
    var articles: List<Article>,
    val status: String,
    val totalResults: Int
)