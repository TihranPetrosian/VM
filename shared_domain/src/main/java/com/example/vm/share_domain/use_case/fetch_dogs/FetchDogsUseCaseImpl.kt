package com.example.vm.share_domain.use_case.fetch_dogs

import com.example.vm.share_domain.model.dogs.PetsVo
import com.example.vm.share_domain.repository.pets.PetsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDogsUseCaseImpl @Inject constructor(
    private val petsRepository: PetsRepository,
) : FetchDogsUseCase {

    override fun execute(): Flow<List<PetsVo>> = petsRepository.fetchDogs()
}
