package com.mbds.newsletter.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ethanhua.skeleton.Skeleton
import com.mbds.newsletter.R
import com.mbds.newsletter.adapters.ArticleAdapter
import com.mbds.newsletter.database.AppDatabase
import com.mbds.newsletter.database.FavoriteViewModel
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.databinding.FragmentArticlesBinding
import com.mbds.newsletter.listeners.ArticleClickListener
import com.mbds.newsletter.models.Article
import com.mbds.newsletter.models.ArticlesResponse
import com.mbds.newsletter.models.Source
import com.mbds.newsletter.repositories.NewsApiRepository
import com.mbds.newsletter.utils.Converter
import com.mbds.newsletter.utils.Endpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArticlesFragment : Fragment(), ArticleClickListener {

    private var source:String = ""
    private var category: String = ""
    private var country: String = ""
    private var action: String = ""
    private var favorites: Array<Favorite> = emptyArray()

    private lateinit var binding: FragmentArticlesBinding
    private val repository: NewsApiRepository = NewsApiRepository()
    private val adapter = ArticleAdapter(mutableListOf(), emptyArray(), this)
    private lateinit var db: FavoriteViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = view.findViewById(R.id.article_recycler_view)
        val adapterRecycler = adapter
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = adapterRecycler

        var skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(adapter)
            .load(R.layout.articles_list)
            .show();

        db = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        db.allFavorites.observe(viewLifecycleOwner, Observer {
                data ->
                    Log.v("ArticlesFragment", "Favorites fetched count : " + data.size)
                    favorites = data.toTypedArray()
                    adapter.favorites = favorites

                    if(action == Endpoints.FAVORITES)
                        adapter.dataset = Converter.favoritesToArticles(favorites)
                    adapter.notifyDataSetChanged()
        })

        if(action != Endpoints.FAVORITES)
            lifecycleScope.launch {
                getData(source = source, category = category, country = country, action =   action)
            }

        skeletonScreen.hide()
    }

    companion object {
        fun newInstance(source: String, country: String, category: String, action:String): ArticlesFragment {
            return ArticlesFragment().apply {
                this.source = source
                this.category = category
                this.country = country
                this.action = action
            }
        }
    }

    private suspend fun getData(source: String, country: String, category: String, action:String) {
        withContext(Dispatchers.IO) {
            var result: ArticlesResponse?

            if(action == Endpoints.EVERYTHING)
                result = repository.everything(category, source)
            else
                result = repository.headlines(country, category)

            val articles = result?.articles
            if(articles != null) {
                Log.v("ArticlesFragment", "Articles fetched count : " + articles.size)
                setData(articles)
            }
        }
    }

    private suspend fun setData( articles : Array<Article>) {
        withContext(Dispatchers.Main) {
            adapter.dataset.addAll(articles)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCustomerClick(article: Article, favoriteId:Int?) {
        Log.v("ArticlesFragment", "Favorite Clicked in Article ")

        if(favoriteId == null)
            db.addFavorite(article)
        else
            db.deleteFavorite(favoriteId)
    }

}