package com.example.vm.share_domain.repository.pets

import com.example.vm.share_domain.model.dogs.PetsVo
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    fun fetchDogs(): Flow<List<PetsVo>>

    fun fetchCats(): Flow<List<PetsVo>>
}
