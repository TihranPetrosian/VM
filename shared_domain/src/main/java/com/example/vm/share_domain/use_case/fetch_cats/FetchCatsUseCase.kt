package com.example.vm.share_domain.use_case.fetch_cats

import com.example.vm.share_domain.model.dogs.PetsVo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface FetchCatsUseCase {

    fun execute(): Flow<List<PetsVo>>
}
