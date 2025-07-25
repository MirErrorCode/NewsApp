package mir.errorcode.newsapp.data.api

import androidx.room.Query
import mir.errorcode.newsapp.data.db.ArticleDao
import mir.errorcode.newsapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService, private val articleDao: ArticleDao) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(countryCode = countryCode, page = pageNumber)


    suspend fun getSearchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)


    fun getFavoriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavorite(article: Article) = articleDao.insert(article = article)

    suspend fun removeFromFavorite(article: Article) {
        articleDao.deleteByUrl(article.url)
    }

    suspend fun isArticleFavorite(url: String): Boolean {
        return articleDao.isArticleFavorite(url)
    }

    suspend fun getFavoriteUrls(): List<String> {
        return articleDao.getAllArticlesList().map { it.url }
    }

    suspend fun deleteArticle(article: Article) {
        articleDao.delete(article)
    }






}