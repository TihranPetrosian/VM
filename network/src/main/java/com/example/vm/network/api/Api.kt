package com.example.vm.network.api

import com.example.vm.network.BuildConfig

internal object Api {

    object TheDogApi {
        const val URL = BuildConfig.THE_DOG_API_BASE_URL
        const val KEY = BuildConfig.THE_DOG_API_KEY
    }

    object TheCatApi{

        const val URL = "https://api.thecatapi.com/v1/";
        // Field from default config.
        const val  KEY = "live_LI1gJyFHKNZ41DlYldRIzw274Zeic7Kb4G2JpNqwSqK1B1WFI1e1xoqojpEYTGpW";

//        const val URL = BuildConfig.THE_CAT_API_BASE_URL
//        const val KEY = BuildConfig.THE_CAT_API_KEY
    }
}