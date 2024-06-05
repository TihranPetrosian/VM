package com.example.vm.share_domain.use_case.fetch_cats

import android.util.Log
import com.example.vm.share_domain.model.cats.CatsVo
import com.example.vm.share_domain.model.dogs.DogsVo
import com.example.vm.share_domain.repository.pets.PetsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCatsUseCaseImpl @Inject constructor(
    private val petsRepository: PetsRepository,
) : FetchCatsUseCase {

    override fun execute(): Flow<List<CatsVo>> {

        Log.d("FetchCatsUseCaseImpl", "execute")

        return petsRepository.fetchCats()
    }


}
