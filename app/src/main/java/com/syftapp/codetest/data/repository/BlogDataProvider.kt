package com.syftapp.codetest.data.repository

import com.syftapp.codetest.data.model.domain.Comment
import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.data.model.domain.User
import io.reactivex.Single

interface BlogDataProvider {

    fun getUsers(): Single<List<User>>

    fun getComments(): Single<List<Comment>>

    fun getPosts(): Single<List<Post>>

}