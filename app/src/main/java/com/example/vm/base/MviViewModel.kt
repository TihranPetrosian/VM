package com.example.vm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.reflect.Method

abstract class MviViewModel<STATE : MviState, ScreenState : MviScreenState, ViewModelState: MviViewModelState, EVENT : MviEvent, EFFECT : MviEffect>(initialState: STATE) : ViewModel() {

    private val state = MutableStateFlow(initialState)
    private val effect = MutableSharedFlow<EFFECT>()

    fun setEvent(event: EVENT) = handleEvent(event)

    protected abstract fun handleEvent(event: EVENT)

    internal fun listenState(): Flow<ScreenState> {
        return state.map {
            @Suppress("UNCHECKED_CAST")
            it.screenState as ScreenState
        }.distinctUntilChanged()
    }

    fun withEffect() = effect.asSharedFlow()

    protected fun withState(reducer: (STATE) -> Unit) = reducer.invoke(state.value)

    protected suspend fun awaitState(): STATE = state.last()

    protected fun setState(reducer: STATE.() -> STATE) {
        state.value = state.value.reducer()
    }

    protected fun setEffect(producer: () -> EFFECT) {
        viewModelScope.launch {
            effect.emit(producer())
        }
    }
}