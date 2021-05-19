package com.syftapp.codetest.di

import com.syftapp.codetest.BuildConfig
import com.syftapp.codetest.data.api.BlogApi
import com.syftapp.codetest.data.api.BlogService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { BlogService.createService(get()) }

    single { BlogApi(get()) }

}