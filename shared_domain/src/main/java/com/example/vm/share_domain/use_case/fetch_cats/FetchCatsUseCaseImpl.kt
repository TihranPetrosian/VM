package com.example.vm.share_domain.use_case.fetch_cats

import com.example.vm.share_domain.model.dogs.PetsVo
import com.example.vm.share_domain.repository.pets.PetsRepository
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCatsUseCaseImpl @Inject constructor(
    private val petsRepository: PetsRepository,
) : FetchCatsUseCase {

    override fun execute(): Flow<List<PetsVo>> = petsRepository.fetchCats()


}
