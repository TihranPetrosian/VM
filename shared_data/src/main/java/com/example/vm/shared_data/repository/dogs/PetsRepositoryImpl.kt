package com.example.vm.shared_data.repository.dogs

import com.example.vm.share_domain.model.dogs.PetsVo
import com.example.vm.share_domain.repository.pets.PetsRepository
import com.example.vm.shared_data.service.dogs.DogResponseModel
import com.example.vm.shared_data.service.dogs.DogsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetsRepositoryImpl @Inject constructor(
    private val dogsApiService: DogsApiService,
) : PetsRepository {

    override fun fetchDogs(): Flow<List<PetsVo>> = flow {
        emit(
            value = dogsApiService.getData().map { it.toVo() },
        )
    }

    override fun fetchCats(): Flow<List<PetsVo>> = flow {
        emit(
            value = dogsApiService.getData().map { it.toVo() },
        )
    }
}

private fun DogResponseModel.toVo(): PetsVo = PetsVo(
    id = id,
    bredFor = bredFor,
    name = name,
    origin = origin,
    referenceImageId = referenceImageId,
)
