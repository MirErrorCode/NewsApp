package mir.errorcode.newsapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mir.errorcode.newsapp.data.api.NewsRepository
import mir.errorcode.newsapp.models.Article
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    val favoriteArticles = repository.getFavoriteArticles()


    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }

    }
}