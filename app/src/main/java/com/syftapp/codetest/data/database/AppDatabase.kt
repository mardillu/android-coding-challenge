package com.syftapp.codetest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.syftapp.codetest.data.dao.CommentDao
import com.syftapp.codetest.data.dao.PostDao
import com.syftapp.codetest.data.dao.UserDao
import com.syftapp.codetest.data.model.domain.Comment
import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.data.model.domain.User

private const val DATABASE_NAME = "blog.db"

@Database(
    entities = [Post::class, User::class, Comment::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        var instance: AppDatabase? = null
        private val syncLock = Object()

        operator fun invoke(context: Context) = instance ?: buildDatabase(context)

        private fun buildDatabase(context: Context): AppDatabase {
            return synchronized(syncLock) {
                instance ?:
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build()
                    .also { instance = it }
            }
        }
    }
}