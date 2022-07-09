package com.fabexamples.newsapplication

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)