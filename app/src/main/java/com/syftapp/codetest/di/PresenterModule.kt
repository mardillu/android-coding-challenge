package com.syftapp.codetest.di

import com.syftapp.codetest.postdetail.PostDetailPresenter
import com.syftapp.codetest.posts.PostsPresenter
import org.koin.dsl.module

val presenterModule = module {

    factory { PostsPresenter(get()) }

    factory { PostDetailPresenter(get()) }

}