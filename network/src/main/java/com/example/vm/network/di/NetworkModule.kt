package com.example.vm.network.di

import com.example.vm.network.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GsonConvertorFactory

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheDogsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheDogsApiInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheDogsApiOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheCatsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheCatsApiInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheCatsApiOkHttpClient

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

    @TheDogsApiInterceptor
    @Provides
    fun provideTheDogApiInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()

        chain.proceed(
            request = request.newBuilder()
                .addHeader("x-api-key", Api.TheDogApi.KEY)
                .build()
        )
    }

    @TheCatsApiInterceptor
    @Provides
    fun provideTheCatsApiInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()

        chain.proceed(
            request = request.newBuilder()
                .addHeader("x-api-key", Api.TheCatApi.KEY)
                .build()
        )
    }

    @TheDogsApiOkHttpClient
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @TheDogsApiInterceptor dogsApiInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(dogsApiInterceptor)
            addInterceptor(httpLoggingInterceptor)
        }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    @TheCatsApiOkHttpClient
    @Provides
    fun provideCatsOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @TheCatsApiInterceptor catsApiInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(catsApiInterceptor)
            addInterceptor(httpLoggingInterceptor)
        }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    @TheDogsRetrofit
    @Provides
    fun provideTheDogsRetrofit(
        @GsonConvertorFactory convertorFactory: Converter.Factory,
        @TheDogsApiOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Api.TheDogApi.URL)
        .addConverterFactory(convertorFactory)
        .client(okHttpClient)
        .build()

    @TheCatsRetrofit
    @Provides
    fun provideTheCatsRetrofit(
        @GsonConvertorFactory convertorFactory: Converter.Factory,
        @TheCatsApiOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Api.TheCatApi.URL)
        .addConverterFactory(convertorFactory)
        .client(okHttpClient)
        .build()
}
