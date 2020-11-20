package com.mbds.newsletter.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.newsletter.R
import com.mbds.newsletter.adapters.ArticleAdapter
import com.mbds.newsletter.databinding.FragmentArticlesBinding
import com.mbds.newsletter.models.Article
import com.mbds.newsletter.models.ArticlesResponse
import com.mbds.newsletter.models.Source
import com.mbds.newsletter.repositories.NewsApiRepository
import com.mbds.newsletter.utils.Endpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArticlesFragment : Fragment() {

    private lateinit var source:String
    private lateinit var category: String
    private lateinit var country: String
    private lateinit var action: String

    private lateinit var binding: FragmentArticlesBinding
    private val repository: NewsApiRepository = NewsApiRepository()
    private val adapter = ArticleAdapter(mutableListOf())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            getData(source = source, category = category, country = country, action =   action)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.article_recycler_view)
        val adapterRecycler = adapter
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = adapterRecycler

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
}