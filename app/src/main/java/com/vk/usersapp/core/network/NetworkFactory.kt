package com.vk.usersapp.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkFactory {

    fun create(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .build()
    }

    private fun gson(): Gson {
        return GsonBuilder()
            .create()
    }

    companion object {
        private const val BASE_URL = "https://dummyjson.com"
        private const val SCHEME = "http"
    }
}