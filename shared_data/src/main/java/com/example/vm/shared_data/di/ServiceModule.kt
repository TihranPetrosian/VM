package com.example.vm.shared_data.di

import com.example.vm.network.di.TheDogsRetrofit
import com.example.vm.shared_data.service.dogs.DogsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun providesDogsApiService(
        @TheDogsRetrofit retrofit: Retrofit,
    ): DogsApiService = retrofit.create()
}