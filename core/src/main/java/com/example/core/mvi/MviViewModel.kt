package com.example.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class MviViewModel<State : MviState, ScreenState : MviScreenState, ViewModelState: MviViewModelState, Event : MviEvent, Effect : MviEffect>(initialState: State) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    private val _effect = MutableSharedFlow<Effect>()

    val state: Flow<ScreenState>
        get() = _state.map {
            @Suppress("UNCHECKED_CAST")
            it.screenState as ScreenState
        }.distinctUntilChanged()

    val effect: Flow<Effect>
        get() = _effect.asSharedFlow()

    fun setEvent(event: Event) = handleEvent(event)

    protected abstract fun handleEvent(event: Event)

    protected fun withState(reducer: (State) -> Unit) = reducer.invoke(_state.value)

    protected suspend fun awaitState(): State = _state.last()

    protected fun setState(reducer: State.() -> State) {
        _state.value = _state.value.reducer()
    }

    protected fun setEffect(producer: () -> Effect) {
        viewModelScope.launch {
            _effect.emit(producer())
        }
    }
}