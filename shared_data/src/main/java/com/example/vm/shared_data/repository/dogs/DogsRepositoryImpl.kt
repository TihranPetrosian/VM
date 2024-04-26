package com.example.vm.shared_data.repository.dogs

import com.example.vm.share_domain.model.dogs.DogVo
import com.example.vm.share_domain.repository.dogs.DogsRepository
import com.example.vm.shared_data.service.dogs.DogResponseModel
import com.example.vm.shared_data.service.dogs.DogsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val dogsApiService: DogsApiService,
) : DogsRepository {

    override fun fetchDogs(): Flow<List<DogVo>> = flow {
        emit(
            value = dogsApiService.getData().map { it.toVo() },
        )
    }
}

private fun DogResponseModel.toVo(): DogVo = DogVo(
    id = id,
    bredFor = bredFor,
    name = name,
    origin = origin,
    referenceImageId = referenceImageId,
)
