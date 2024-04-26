package com.example.vm.share_domain.use_case.fetch_dogs

import com.example.vm.share_domain.model.dogs.DogVo
import kotlinx.coroutines.flow.Flow

interface FetchDogsUseCase {

    fun execute(): Flow<List<DogVo>>
}
