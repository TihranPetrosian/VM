package com.example.vm.shared_data.service.cats

import retrofit2.http.GET

interface CatsApiService {

    @GET("breeds")
    suspend fun getData(): List<CatResponseModel>
}