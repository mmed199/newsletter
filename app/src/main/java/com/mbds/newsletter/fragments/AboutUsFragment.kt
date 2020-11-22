package com.mbds.newsletter.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mbds.newsletter.databinding.FragmentAboutUsBinding
import com.mbds.newsletter.databinding.FragmentArticleDetailsBinding


class AboutUsFragment : Fragment() {

    lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.githubButton.setOnClickListener {
            val uri: Uri = Uri.parse("https://github.com/mmed199/newsletter")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }
}