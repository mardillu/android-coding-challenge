package com.syftapp.codetest.data.api

import com.syftapp.codetest.data.model.api.Comment
import com.syftapp.codetest.data.model.api.Post
import com.syftapp.codetest.data.model.api.User
import com.syftapp.codetest.util.DOWNLOAD_PAGE_SIZE
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogService {

    @GET("/users")
    fun getUsers(): Single<List<User>>

    @GET("/comments")
    fun getComments(): Single<List<Comment>>

    @GET("/posts")
    fun getPosts(@Query("_page") page: Int = 1,
                 @Query("_limit") limit: Int = DOWNLOAD_PAGE_SIZE): Single<List<Post>>

    companion object {
        fun createService(retrofit: Retrofit) = retrofit.create(BlogService::class.java)
    }

}