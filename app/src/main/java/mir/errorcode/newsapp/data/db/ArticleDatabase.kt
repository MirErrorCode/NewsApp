package mir.errorcode.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mir.errorcode.newsapp.models.Article


@Database(entities = [Article::class], version = 3, exportSchema = true)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao



}
