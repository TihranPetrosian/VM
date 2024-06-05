package com.example.vm.shared_data.repository.dogs

import com.example.vm.share_domain.model.cats.CatsVo
import com.example.vm.share_domain.model.dogs.DogsVo
import com.example.vm.share_domain.repository.pets.PetsRepository
import com.example.vm.shared_data.service.cats.CatResponseModel
import com.example.vm.shared_data.service.cats.CatsApiService
import com.example.vm.shared_data.service.dogs.DogResponseModel
import com.example.vm.shared_data.service.dogs.DogsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetsRepositoryImpl @Inject constructor(
    private val dogsApiService: DogsApiService,
    private val catsApiService: CatsApiService,
) : PetsRepository {

    override fun fetchDogs(): Flow<List<DogsVo>> = flow {
        emit(
            value = dogsApiService.getData().map { it.toVo() },
        )
    }

    override fun fetchCats(): Flow<List<CatsVo>> = flow {
        emit(
            value = catsApiService.getData().map { it.toVo() },
        )
    }
}

private fun DogResponseModel.toVo(): DogsVo = DogsVo(
    id = id,
    bredFor = bredFor,
    name = name,
    origin = origin,
    referenceImageId = referenceImageId,
)

private fun CatResponseModel.toVo(): CatsVo = CatsVo(
    id = id,
    bredFor = bredFor,
    name = name,
    origin = origin,
    referenceImageId = referenceImageId,
)
