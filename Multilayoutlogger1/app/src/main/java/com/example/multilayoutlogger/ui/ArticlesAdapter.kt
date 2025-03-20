package com.example.multilayoutlogger.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.multilayoutlogger.R
import com.example.multilayoutlogger.data.Article

class ArticlesAdapter(
    private val onArticleClick: (Article) -> Unit,
    private val onFavoriteClick: (Article) -> Unit
) : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.textTitle)
        private val previewText: TextView = itemView.findViewById(R.id.textPreview)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.buttonFavorite)

        fun bind(article: Article) {
            titleText.text = article.title
            previewText.text = article.content
            favoriteButton.setImageResource(
                if (article.isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )

            itemView.setOnClickListener { onArticleClick(article) }
            favoriteButton.setOnClickListener { onFavoriteClick(article) }
        }
    }
}

private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
} 