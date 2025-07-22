package mir.errorcode.newsapp.data.api


import mir.errorcode.newsapp.models.NewsResponse
import mir.errorcode.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}

