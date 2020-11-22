package com.mbds.newsletter.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbds.newsletter.R
import com.mbds.newsletter.database.FavoriteViewModel
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.databinding.ArticlesListBinding
import com.mbds.newsletter.listeners.ArticleClickListener
import com.mbds.newsletter.models.Article

public class ArticleAdapter(var dataset: MutableList<Article>, var favorites: Array<Favorite>, val articleClickListener: ArticleClickListener) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {



    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {

        lateinit var binding : ArticlesListBinding

        fun bind(item: Article, articleClickListener: ArticleClickListener, favoriteId: Int?) {

            binding.articleTitle.text = item.title
            binding.articleAuthor.text = "By :" + (item.author?:"None")
            binding.articleDescription.text = item.description

            if(favoriteId != null)
                binding.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on)
            else
                binding.favoriteButton.setImageResource(android.R.drawable.btn_star_big_off)

            Glide.with(root)
                    .load(item.urlToImage)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .into(binding.articleImage);


            binding.favoriteButton.setOnClickListener {
                articleClickListener.onCustomerClick(item, favoriteId)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.articles_list, parent, false)

        val viewHolder = ViewHolder(rootView)
        viewHolder.binding = ArticlesListBinding.bind(rootView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var favoriteId:Int? = null
        favorites.forEach {
            if(dataset[position].url == it.url) favoriteId = it.id
        }

        holder.bind(
                dataset[position], articleClickListener, favoriteId
        )
    }

    override fun getItemCount(): Int = dataset.size


}
