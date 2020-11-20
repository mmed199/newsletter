package com.mbds.newsletter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbds.newsletter.R
import com.mbds.newsletter.databinding.ArticlesListBinding
import com.mbds.newsletter.models.Article

public class ArticleAdapter(public var dataset: MutableList<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {

        lateinit var binding : ArticlesListBinding

        fun bind(item: Article) {

            binding.articleTitle.text = item.title
            binding.articleAuthor.text = "By :" + (item.author?:"None")
            binding.articleDescription.text = item.description



            Glide.with(root)
                    .load(item.urlToImage)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .into(binding.articleImage);


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
        holder.bind(
                dataset[position]
        )
    }

    override fun getItemCount(): Int = dataset.size


}
