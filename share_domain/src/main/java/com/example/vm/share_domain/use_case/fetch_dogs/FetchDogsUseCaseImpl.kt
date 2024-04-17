package com.example.vm.share_domain.use_case.fetch_dogs

import com.example.vm.share_domain.model.dogs.DogVo
import com.example.vm.share_domain.repository.dogs.DogsRepository
import com.example.vm.share_domain.state.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDogsUseCaseImpl @Inject constructor(
    private val dogsRepository: DogsRepository,
) : FetchDogsUseCase {

    override fun execute(): Flow<Resource<List<DogVo>>> = dogsRepository.fetchDogs()
}
