package com.example.vm.share_domain.state

sealed interface Resource<T> {

    data class Loading<T>(val data: T?) : Resource<T>

    data class Success<T>(val data: T) : Resource<T>

    data class Error<T>(val error: Throwable) : Resource<T>
}
