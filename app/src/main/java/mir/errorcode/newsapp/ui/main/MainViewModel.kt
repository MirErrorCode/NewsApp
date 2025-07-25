package mir.errorcode.newsapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mir.errorcode.newsapp.data.api.NewsRepository
import mir.errorcode.newsapp.models.Article
import mir.errorcode.newsapp.models.NewsResponse
import mir.errorcode.newsapp.utils.Resource
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {


    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val newsPage = 1

    init {
        getNews("us")
    }


     fun getNews(countryCode: String) {
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    val favoriteUrls = repository.getFavoriteUrls()
                    res.articles.forEach { article ->
                        article.isFavorite = favoriteUrls.contains(article.url)
                    }
                    newsLiveData.postValue(Resource.Success(res))
                }
            } else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

    fun toggleFavorite(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val url = article.url ?: return@launch
        val isFavorite = repository.isArticleFavorite(url)
        if (isFavorite) {
            repository.removeFromFavorite(article)
        } else {
            repository.addToFavorite(article)
        }
    }


}