package com.example.vm.shared_data.service.dogs

import retrofit2.http.GET

interface DogsApiService {

    @GET("breeds")
    suspend fun getData(): List<DogResponseModel>
}