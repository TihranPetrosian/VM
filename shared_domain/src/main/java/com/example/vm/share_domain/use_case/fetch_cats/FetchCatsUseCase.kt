package com.example.vm.share_domain.use_case.fetch_cats

import com.example.vm.share_domain.model.cats.CatsVo
import com.example.vm.share_domain.model.dogs.DogsVo
import kotlinx.coroutines.flow.Flow

interface FetchCatsUseCase {

    fun execute(): Flow<List<CatsVo>>
}
