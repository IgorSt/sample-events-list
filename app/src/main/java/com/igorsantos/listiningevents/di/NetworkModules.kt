package com.igorsantos.listiningevents.di

import com.google.gson.Gson
import com.igorsantos.listiningevents.domain.network.EventsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    private const val BASE_URL = "http://5f5a8f24d44d640016169133.mockapi.io/api/"

    @Singleton
    @Provides
    fun eventService(gson: Gson): EventsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(EventsService::class.java)
    }
}