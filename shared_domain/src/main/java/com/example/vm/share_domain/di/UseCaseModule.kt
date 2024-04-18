package com.example.vm.share_domain.di

import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun fetchDogsUseCase(useCase: FetchDogsUseCaseImpl): FetchDogsUseCase
}