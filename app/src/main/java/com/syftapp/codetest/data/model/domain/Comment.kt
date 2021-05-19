package com.syftapp.codetest.data.model.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "comment",
    foreignKeys = [
        ForeignKey(
            entity = Post::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("post_id")
        )
    ]
)
data class Comment(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "post_id")
    val postId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "body")
    val body: String
)