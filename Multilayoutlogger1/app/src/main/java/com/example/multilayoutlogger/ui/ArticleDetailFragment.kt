package com.example.multilayoutlogger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.multilayoutlogger.R
import com.example.multilayoutlogger.databinding.FragmentArticleDetailBinding
import com.example.multilayoutlogger.viewmodel.NewsViewModel

class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeArticle()
    }

    private fun observeArticle() {
        viewModel.getArticleById(args.articleId).observe(viewLifecycleOwner) { article ->
            article?.let {
                binding.textTitle.text = it.title
                binding.textContent.text = it.content
                binding.buttonFavorite.setImageResource(
                    if (it.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
                binding.buttonFavorite.setOnClickListener { _ ->
                    viewModel.toggleFavorite(article)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 