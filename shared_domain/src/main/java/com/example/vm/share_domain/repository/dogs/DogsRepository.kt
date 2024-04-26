package com.example.vm.share_domain.repository.dogs

import com.example.vm.share_domain.model.dogs.DogVo
import kotlinx.coroutines.flow.Flow

interface DogsRepository {

    fun fetchDogs(): Flow<List<DogVo>>
}
