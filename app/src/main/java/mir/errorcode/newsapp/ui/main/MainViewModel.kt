package mir.errorcode.newsapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mir.errorcode.newsapp.data.api.NewsRepository
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


    private fun getNews(countryCode: String) {
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if(response.isSuccessful){
                response.body().let { res ->
                    newsLiveData.postValue(Resource.Success(res))

                }
            }else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }


}