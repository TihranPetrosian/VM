package com.example.vm.shared_data.di

import com.example.vm.share_domain.repository.dogs.DogsRepository
import com.example.vm.shared_data.repository.dogs.DogsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsDogsRepository(repository: DogsRepositoryImpl): DogsRepository
}
