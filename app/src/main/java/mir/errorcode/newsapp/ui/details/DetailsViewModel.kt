package mir.errorcode.newsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mir.errorcode.newsapp.data.api.NewsRepository
import mir.errorcode.newsapp.models.Article
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    init {
        getSavedArticles()
    }

    fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO) {
        val res = repository.getFavoriteArticles()
        repository.getFavoriteArticles()
    }

    suspend fun toggleFavorite(article: Article) {
        val url = article.url
        val isFavorite = repository.isArticleFavorite(url)
        if (isFavorite) {
            repository.removeFromFavorite(article)
        } else {
            repository.addToFavorite(article)
        }
    }


    suspend fun isFavorite(article: Article): Boolean {
        val url = article.url
        return repository.isArticleFavorite(url)
    }

}