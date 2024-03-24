package com.example.vm.api

import retrofit2.create

object Common {

    val retrofitService : ApiInterface = DogsBuilder.retrofit.create()
}