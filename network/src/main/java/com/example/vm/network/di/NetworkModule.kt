package com.example.vm.network.di

import com.example.vm.network.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GsonConvertorFactory

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheDogsRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @GsonConvertorFactory
    @Provides
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            this.addInterceptor(httpLoggingInterceptor)
        }.build()

    @TheDogsRetrofit
    @Provides
    fun provideTheDogsRetrofit(
        @GsonConvertorFactory convertorFactory: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Api.THE_DOG_API_BASE_URL)
        .addConverterFactory(convertorFactory)
        .client(okHttpClient)
        .build()
}
