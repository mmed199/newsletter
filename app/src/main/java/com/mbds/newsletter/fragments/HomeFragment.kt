package com.mbds.newsletter.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mbds.newsletter.R
import com.mbds.newsletter.databinding.HomeFragmentBinding
import com.mbds.newsletter.utils.Countries
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val countriesUtil : Countries = Countries()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
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
    }

    fun categoriesAlert(){

        val builder = AlertDialog.Builder(activity)
        with(builder)
        {
            setTitle(R.string.pick_category)
            setItems(R.array.categories, DialogInterface.OnClickListener { dialog, which ->
                //TODO open new Fragment to display Articles
                var categories = getResources().getStringArray(R.array.categories);
                Log.v("HomeFragement", "Category clicked " + categories[which])
            })
            show()
        }

    }

    fun countriesAlert() {
        val builder = AlertDialog.Builder(activity)

        with(builder) {
            setTitle(R.string.pick_country)
            val countries = countriesUtil.names
            setItems(
                countries,
                { dialog, which ->
                    //TODO open new Fragment to display Articles
                    Log.v("HomeFragement", "Country clicked " + countriesUtil.getISOCode(which))
                }
            )
            show()
        }
    }
}


