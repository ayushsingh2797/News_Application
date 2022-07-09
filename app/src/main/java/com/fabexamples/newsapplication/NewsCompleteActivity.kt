package com.fabexamples.newsapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.fabexamples.newsapplication.databinding.ActivityMainBinding
import com.fabexamples.newsapplication.databinding.ActivityNewsCompleteBinding
import com.squareup.picasso.Picasso

class NewsCompleteActivity : AppCompatActivity() {

    var title = ""
    var author = ""
    var description = ""
    private var content = ""
    private var imageUrl = ""
    private var url = ""
    private var publishedAt = ""
    var source = ""
    var isLiked = false
    lateinit var newsCompleteBinding: ActivityNewsCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsCompleteBinding = DataBindingUtil.setContentView(this,R.layout.activity_news_complete)
        getDataFromIntent(intent)
        bindViews()

    }

    private fun bindViews() {
        newsCompleteBinding.let {
            if (UtilityFunctions.checkIsNotNull(imageUrl)) {
                it.ivCompleteImage.visibility = View.VISIBLE
                Picasso.get().load(imageUrl).into(it.ivCompleteImage)
            }
            it.tvCompleteTitle.text = title
            if (UtilityFunctions.checkIsNotNull(description)) {
                it.tvCompleteTitleDesc.visibility = View.VISIBLE
                it.tvCompleteTitleDesc.text =description
            }
            if (UtilityFunctions.checkIsNotNull(author)) {
                it.tvAuthor.visibility = View.VISIBLE
                it.tvAuthor.text = author
            }
            if (UtilityFunctions.checkIsNotNull(publishedAt)) {
                it.tvPublishedAt.visibility = View.VISIBLE
                it.tvPublishedAt.text = UtilityFunctions.getDateFromString(publishedAt)
            }
            if (UtilityFunctions.checkIsNotNull(content)){
                it.tvCompleteContent.visibility= View.VISIBLE
                it.tvCompleteContent.text = content
            }
            if (UtilityFunctions.checkIsNotNull(url)){
                it.btReadFullNews.visibility = View.VISIBLE
                it.btReadFullNews.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
            }
            it.ivLikeBtn.isChecked = isLiked
        }
    }


    private fun getDataFromIntent(intent: Intent?) {
        title = intent?.getStringExtra("title").toString()
        url = intent?.getStringExtra("url").toString()
        author = intent?.getStringExtra("author").toString()
        description = intent?.getStringExtra("description").toString()
        imageUrl = intent?.getStringExtra("urlToImage").toString()
        publishedAt = intent?.getStringExtra("publishedAt").toString()
        source = intent?.getStringExtra("source").toString()
        content = intent?.getStringExtra("content").toString()
        isLiked = intent?.getBooleanExtra("isLiked",false) == true

    }
}