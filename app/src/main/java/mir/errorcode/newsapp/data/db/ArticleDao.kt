package mir.errorcode.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mir.errorcode.newsapp.models.Article


@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE url = :url)")
    suspend fun isArticleFavorite(url: String): Boolean

    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun deleteByUrl(url: String)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticlesList(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)



}