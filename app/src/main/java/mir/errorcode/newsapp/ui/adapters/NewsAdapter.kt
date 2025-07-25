package mir.errorcode.newsapp.ui.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mir.errorcode.newsapp.R
import mir.errorcode.newsapp.databinding.ItemArticleBinding
import mir.errorcode.newsapp.models.Article
import mir.errorcode.newsapp.utils.Utils


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val article = differ.currentList[position]
        holder.binding.apply {
            Glide.with(root).load(article.urlToImage).into(articleImage)
            articleImage.clipToOutline = true
            articleTitle.text = article.title
            articleDate.text = Utils.formatDate(article.publishedAt)

            root.setOnClickListener {
                onItemClickListener?.invoke(article)
            }

            iconFavorite.setOnClickListener {
                onFavoriteClickListener?.invoke(article)
            }
        }

        holder.binding.iconFavorite.setImageResource(
            if (article.isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite
        )

        holder.binding.iconFavorite.setOnClickListener {
            article.isFavorite = !article.isFavorite
            notifyItemChanged(holder.adapterPosition)
            onFavoriteClickListener?.invoke(article)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }


    private var onFavoriteClickListener: ((Article) -> Unit)? = null

    fun setOnFavoriteClickListener(listener: (Article) -> Unit) {
        onFavoriteClickListener = listener
    }

}