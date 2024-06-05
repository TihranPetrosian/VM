package com.example.vm.share_domain.repository.pets

import com.example.vm.share_domain.model.cats.CatsVo
import com.example.vm.share_domain.model.dogs.DogsVo
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    fun fetchDogs(): Flow<List<DogsVo>>

    fun fetchCats(): Flow<List<CatsVo>>
}
