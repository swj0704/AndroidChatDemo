package com.wonjoon.androidchatdemo.di

import com.wonjoon.androidchatdemo.BuildConfig
import com.wonjoon.data.API
import com.wonjoon.data.ChatRepositoryImpl
import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.usecase.GetChatRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideGetChatRoomUseCase() : GetChatRoomUseCase{
        return GetChatRoomUseCase(provideRepository())
    }

    @Provides
    @Singleton
    fun provideRepository() : ChatRepository{
        return ChatRepositoryImpl(provideApi())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter() : Converter.Factory{
        return GsonConverterFactory.create() as Converter.Factory
    }

    @Provides
    @Singleton
    fun provideApi() : API{
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(provideGsonConverter())
            .client(provideOkHttpClient())
            .build()
            .create(API::class.java)
    }
}