package com.elnaz.theguardiannewsfeed.services.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elnaz.theguardiannewsfeed.data.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase: RoomDatabase() {
    abstract val articleDao: ArticleDao
}