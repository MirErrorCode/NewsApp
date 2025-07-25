package mir.errorcode.newsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mir.errorcode.newsapp.data.api.NewsService
import mir.errorcode.newsapp.data.db.ArticleDao
import mir.errorcode.newsapp.data.db.ArticleDatabase
import mir.errorcode.newsapp.models.Article
import mir.errorcode.newsapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun baseUrl() = BASE_URL


    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)


    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logging())
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String) : NewsService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(NewsService::class.java)

    @Provides
    @Singleton
    fun providerArticleDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "article_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideArticleDao(appDatabase: ArticleDatabase): ArticleDao {
        return appDatabase.getArticleDao()
    }

}