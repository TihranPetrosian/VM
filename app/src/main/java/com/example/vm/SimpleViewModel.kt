package com.example.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vm.api.Common
import com.example.vm.recycler.DogsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SimpleViewModel: ViewModel() {

    private val viewModelStateFlow = MutableStateFlow(emptyList<DogsItem>())
    private val _stateFlow = MutableStateFlow(emptyList<DogsItem>())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val dogs = runCatching {
                Common.retrofitService.getData()
            }.onSuccess {
                _stateFlow.emit(it)
                viewModelStateFlow.emit(it)
            }.onFailure {
                Log.e("Error", "onFailure: ", it)
            }.getOrDefault(emptyList<DogsItem>())
        }
    }

    fun searchDogs(searchText: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                _stateFlow.emit(
                    viewModelStateFlow.value.filter {
                        it.name?.contains(searchText) == true
                                ||it.bred_for?.contains(searchText) == true
                    }
                )
            }
        }
    }
}