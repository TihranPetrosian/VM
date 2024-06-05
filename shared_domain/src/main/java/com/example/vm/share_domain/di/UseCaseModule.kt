package com.example.vm.share_domain.di

import com.example.vm.share_domain.use_case.fetch_cats.FetchCatsUseCase
import com.example.vm.share_domain.use_case.fetch_cats.FetchCatsUseCaseImpl
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun fetchDogsUseCase(useCase: FetchDogsUseCaseImpl): FetchDogsUseCase

    @Binds
    fun fetchCatsUseCase( useCase: FetchCatsUseCaseImpl): FetchCatsUseCase

//    @Binds
//    fun fetchCatsUseCase(@CatsUseCase useCase: FetchCatsUseCaseImpl): FetchPetsUseCase

}
