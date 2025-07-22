package mir.errorcode.newsapp.data.api


import mir.errorcode.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("g") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    )


    suspend fun getHeadlines(
        @Query("country") countryCode : String = "ua",
        @Query("page") page : Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    )

}