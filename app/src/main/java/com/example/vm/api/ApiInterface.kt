package com.example.vm.api

import com.example.vm.recycler.DogsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("breeds")
    suspend fun getData(): MutableList<DogsItem>

}