package com.fabexamples.newsapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabexamples.newsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterListenerInterface {

    lateinit var newsRV :RecyclerView
    lateinit var mainActivityBinding: ActivityMainBinding
    lateinit var newsRVAdapter : NewsRVAdapter
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        newsRV = mainActivityBinding.rvNewsItems
        newsRV.layoutManager = LinearLayoutManager(this)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        fetchDataFromServer()
        observeLiveData()
        setClickListeners()
    }

    private fun setClickListeners() {
       mainActivityBinding.tvError.setOnClickListener{
           if(UtilityFunctions.isConnectingToInternet(applicationContext)){
                fetchDataFromServer()
           }
           else
               Toast.makeText(applicationContext,"Please check your internet",Toast.LENGTH_SHORT).show()
       }
    }

    private fun observeLiveData() {
       newsViewModel.newsArticlesLiveData?.observe(this, Observer<NewsModel> { t->
           if(t!= null){
               setAdapter(t.articles)
           }
           else {
               mainActivityBinding.rvNewsItems.visibility = View.GONE
               mainActivityBinding.progressBar.visibility = View.GONE
               mainActivityBinding.rlError.visibility = View.VISIBLE
           }
       })
    }

    private fun setAdapter(articleList: List<Article>) {
        mainActivityBinding.progressBar.visibility = View.GONE
        newsRVAdapter = NewsRVAdapter(this,articleList,this)
        mainActivityBinding.rvNewsItems.adapter = newsRVAdapter
    }

    private fun fetchDataFromServer(){
        if(UtilityFunctions.isConnectingToInternet(applicationContext)){
            mainActivityBinding.progressBar.visibility = View.VISIBLE
            newsViewModel.fetchNewsArticle()
        }
    }

    override fun openCompleteNewsActivity(intent: Intent) {

        val intent1 = Intent(this,NewsCompleteActivity::class.java)
        intent1.putExtras(intent)
        startActivityForResult(intent1,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==100 && resultCode == 200 && data!=null){
            if(data.hasExtra("isLikedStatus") && data.hasExtra("position")){
                val position = data.getIntExtra("position",-1)
                val isLikedStatus = data.getBooleanExtra("isLikedStatus",false)
                if (position>=0) {
                    newsViewModel.newsArticlesLiveData?.value?.articles?.get(position)?.isLiked = isLikedStatus
                    newsRVAdapter.notifyItemChanged(position)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}