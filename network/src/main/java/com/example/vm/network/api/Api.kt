package com.example.vm.network.api

import com.example.vm.network.BuildConfig

internal object Api {

    object TheDogApi {
        const val URL = BuildConfig.THE_DOG_API_BASE_URL
        const val KEY = BuildConfig.THE_DOG_API_KEY
    }
}