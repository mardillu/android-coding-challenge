package com.syftapp.codetest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syftapp.codetest.data.model.domain.Comment
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment")
    fun getAll(): Single<List<Comment>>

    @Query("SELECT * FROM comment WHERE id = :id")
    fun getById(id: Int): Single<Comment>

    @Query("SELECT * FROM comment WHERE post_id = :postId")
    fun getForPost(postId: Int): Single<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg comments: Comment): Completable
}