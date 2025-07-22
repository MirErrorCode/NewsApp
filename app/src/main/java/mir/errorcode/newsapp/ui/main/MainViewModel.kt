package mir.errorcode.newsapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mir.errorcode.newsapp.data.api.TestRepo
import mir.errorcode.newsapp.models.NewsResponse
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TestRepo ): ViewModel() {

    private val _all = MutableLiveData<NewsResponse>()
    val all : LiveData<NewsResponse>
        get() = _all

    init {
        getAll()
    }




    fun getAll() = viewModelScope.launch {
        repository.getAll().let {
            if(it.isSuccessful ) {
                Log.d("checkData", "Response: ${it.body()}")
                _all.postValue(it.body())
            } else {
                Log.e("checkData", "Failed: ${it.errorBody()?.string()}")
                Log.d("checkData", " Failed to load articles : ${it.errorBody()}")
            }
        }
    }

}