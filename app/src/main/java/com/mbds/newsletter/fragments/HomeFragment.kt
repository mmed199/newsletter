package com.mbds.newsletter.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mbds.newsletter.MainActivity
import com.mbds.newsletter.R
import com.mbds.newsletter.changeFragment
import com.mbds.newsletter.databinding.FragmentHomeBinding
import com.mbds.newsletter.models.Source
import com.mbds.newsletter.repositories.NewsApiRepository
import com.mbds.newsletter.utils.Countries
import com.mbds.newsletter.utils.Endpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val countriesUtil : Countries = Countries()
    private val repository: NewsApiRepository = NewsApiRepository()
    private var sources: Array<Source> =  emptyArray()

    private var editor: String = ""
    private var country: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCategory.setOnClickListener {
            categoriesAlert()
        }

        binding.buttonCountry.setOnClickListener {
            countriesAlert()
        }

        binding.buttonEditor.setOnClickListener {
            editorsAlert()
        }

        binding.buttonSearch.setOnClickListener {
            val category = binding.textCategory.text as String



            if(category.isNotEmpty() || country.isNotEmpty() || editor.isNotEmpty()) {
                if(country != "" && editor == "") {
                    (binding.root.context as? MainActivity)?.changeFragment(
                            ArticlesFragment.newInstance(source = editor,country = country,category = category, action =   Endpoints.HEADLINES)
                    )
                } else {
                    (binding.root.context as? MainActivity)?.changeFragment(
                            ArticlesFragment.newInstance(source = editor, country = country, category = category, action =  Endpoints.EVERYTHING)
                    )
                }
            }
        }

        binding.buttonClear.setOnClickListener {
            binding.textEditor.text = ""
            binding.textCountry.text = ""
            binding.textCategory.text = ""
            
            lifecycleScope.launch {
                getSources()
            }
        }



        lifecycleScope.launch {
            getSources()
        }

    }

    fun categoriesAlert(){

        val builder = AlertDialog.Builder(activity)
        with(builder)
        {
            setTitle(R.string.pick_category)
            setItems(R.array.categories) { dialog, which ->
                //TODO open new Fragment to display Articles
                val categories = resources.getStringArray(R.array.categories)
                binding.textCategory.text = categories[which]
                Log.v("HomeFragment", "Category clicked " + categories[which])
            }
            show()
        }

    }

    fun countriesAlert() {
        val builder = AlertDialog.Builder(activity)

        with(builder) {
            setTitle(R.string.pick_country)
            val countries = countriesUtil.names
            setItems(
                countries
            ) { dialog, which ->
                //TODO open new Fragment to display Articles
                Log.v("HomeFragment", "Country clicked " + countriesUtil.getISOCode(which))
                binding.textCountry.text = countries[which]
                country = countriesUtil.getISOCode(which) ?: ""
                lifecycleScope.launch {
                    getSources(binding.textCategory.text as String, countriesUtil.getISOCode(which) as String)
                }
            }
            show()
        }
    }

    private fun editorsAlert() {
        val builder = AlertDialog.Builder(activity)

        with(builder) {
            setTitle(R.string.pick_editor)

            val sourceNames: Array<String> = Array(sources.size, {""})
            sources.forEachIndexed { index, source ->
                sourceNames[index] = source.name
            }
            setItems(
                    sourceNames
            ) { _, which ->
                //TODO open new Fragment to display Articles
                Log.v("HomeFragment", "Source clicked " + sources[which])
                binding.textEditor.text = sourceNames[which]
                editor  = sources[which].id
            }
            show()
        }
    }

    private suspend fun getSources(category: String = "", country: String = "") {
        withContext(Dispatchers.IO) {
            val result = repository.sources(category, country)
            val sources = result?.sources
            if(sources!= null) setData(sources)
        }
    }

    private suspend fun setData( sourcesData : Array<Source>) {
        withContext(Dispatchers.Main) {
            sources = sourcesData
        }
    }
}


