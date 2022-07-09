package com.fabexamples.newsapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabexamples.newsapplication.databinding.NewsRvItemBinding
import com.squareup.picasso.Picasso

class NewsRVAdapter(context: Context, articlesList: List<Article>?) :
    RecyclerView.Adapter<NewsRVAdapter.ViewHolder>() {

    private var mArticlesList = articlesList
    var mContext = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsRvItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(mArticlesList!![position], position)

    override fun getItemCount(): Int {
        return mArticlesList!!.size
    }

    inner class ViewHolder(private val binding: NewsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article, position: Int) {
            with(binding) {
                tvNewsHeading.text = item.title
                if (UtilityFunctions.checkIsNotNull(item.description)) {
                    tvNewsSubtitle.visibility = View.VISIBLE
                    tvNewsSubtitle.text = item.description
                }
                if (UtilityFunctions.checkIsNotNull(item.urlToImage)) {
                    ivNewsImage.visibility = View.VISIBLE
                    Picasso.get().load(item.urlToImage).into(ivNewsImage)
                }
                ivLikeBtn.setOnClickListener {
                    item.isLiked = ivLikeBtn.isChecked
                }


            }
            itemView.setOnClickListener {
                val intent = Intent(mContext, NewsCompleteActivity::class.java)
                intent.putExtra("url", item.url)
                intent.putExtra("title", item.title)
                intent.putExtra("author", item.author)
                intent.putExtra("content", item.content)
                intent.putExtra("urlToImage", item.urlToImage)
                intent.putExtra("description", item.description)
                intent.putExtra("publishedAt", item.publishedAt)
                intent.putExtra("source", item.source.name)
                intent.putExtra("position", position)
                intent.putExtra("isLiked",item.isLiked)
                mContext.startActivity(intent)
            }
        }
    }
}
