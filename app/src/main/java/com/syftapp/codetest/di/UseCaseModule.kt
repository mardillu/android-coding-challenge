package com.syftapp.codetest.di

import com.syftapp.codetest.postdetail.GetPostUseCase
import com.syftapp.codetest.posts.GetPostsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetPostsUseCase(get()) }

    factory { GetPostUseCase(get()) }

}