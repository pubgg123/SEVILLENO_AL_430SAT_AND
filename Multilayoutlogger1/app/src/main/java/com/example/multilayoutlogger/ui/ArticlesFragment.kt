package com.example.multilayoutlogger.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multilayoutlogger.R
import com.example.multilayoutlogger.databinding.FragmentArticlesBinding
import com.example.multilayoutlogger.viewmodel.NewsViewModel

class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeArticles()
    }

    private fun setupRecyclerView() {
        adapter = ArticlesAdapter(
            onArticleClick = { article ->
                findNavController().navigate(
                    ArticlesFragmentDirections.actionArticlesToDetail(article.id)
                )
            },
            onFavoriteClick = { article ->
                viewModel.toggleFavorite(article)
            }
        )

        binding.recyclerArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ArticlesFragment.adapter
        }
    }

    private fun observeArticles() {
        viewModel.allArticles.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles)
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                refreshArticles()
                true
            }
            R.id.action_settings -> {
                // TODO: Implement settings
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun refreshArticles() {
        binding.progressBar.visibility = View.VISIBLE
        // TODO: Implement refresh logic in ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 