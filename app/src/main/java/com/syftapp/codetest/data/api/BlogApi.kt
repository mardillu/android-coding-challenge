package com.syftapp.codetest.data.api

import com.syftapp.codetest.data.model.domain.Post
import com.syftapp.codetest.data.model.mapper.CommentMapper
import com.syftapp.codetest.data.model.mapper.PostMapper
import com.syftapp.codetest.data.model.mapper.UserMapper
import com.syftapp.codetest.data.model.mapper.apiToDomain
import com.syftapp.codetest.data.repository.BlogDataProvider
import io.reactivex.Single

class BlogApi(private val blogService: BlogService) : BlogDataProvider {

    override fun getUsers() = blogService.getUsers().map { it.apiToDomain(UserMapper) }

    override fun getComments() = blogService.getComments().map { it.apiToDomain(CommentMapper) }

    override fun getPosts(page: Int, skipCache: Boolean): Single<List<Post>> = blogService.getPosts(page).map { it.apiToDomain(PostMapper) }

}