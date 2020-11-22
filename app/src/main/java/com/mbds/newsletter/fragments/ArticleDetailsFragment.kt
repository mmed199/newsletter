package com.mbds.newsletter.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mbds.newsletter.R
import com.mbds.newsletter.database.FavoriteViewModel
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.databinding.ArticlesListBinding
import com.mbds.newsletter.databinding.FragmentArticleDetailsBinding
import com.mbds.newsletter.models.Article


class ArticleDetailsFragment : Fragment() {

    lateinit var article:Article
    lateinit var binding: FragmentArticleDetailsBinding
    var favoriteId: Int? = null
    private lateinit var db: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.articleDetailTitle.text = article.title
        binding.articleDetailContent.text = article.content
        binding.articleDetailDescription.text = article.description
        binding.articleDetailAuthor.text = "By :" + (article.author?:" None")
        binding.articleDetailDate.text = "Published at : " + article.publishedAt.split("T")[0]

        updateButton();

        binding.readMoreButton.setOnClickListener {
            val uri: Uri = Uri.parse(article.url)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.favoriteDetailButton.setOnClickListener {
            if(favoriteId == null) {
                db.addFavorite(article)
                favoriteId = 0
            }
            else {
                db.deleteFavorite(favoriteId!!)
                favoriteId = null
            }

            updateButton();
        }

        Glide.with(binding.root)
            .load(article.urlToImage)
            .fitCenter()
            .placeholder(R.drawable.placeholder)
            .into(binding.articleDetailImage);
    }

    fun updateButton() {
        if(favoriteId != null)
            binding.favoriteDetailButton.setImageResource(android.R.drawable.btn_star_big_on)
        else
            binding.favoriteDetailButton.setImageResource(android.R.drawable.btn_star_big_off)
    }
    companion object {
        fun newInstance(article:Article, favoriteId: Int?) =
                ArticleDetailsFragment().apply {
                    this.article = article
                    this.favoriteId = favoriteId
                }
    }
}