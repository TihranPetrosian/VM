package com.example.core.mvi

abstract class MviState {
    abstract val viewModelState: MviViewModelState
    abstract val screenState: MviScreenState
}

interface MviViewModelState

interface MviScreenState

interface MviEvent

interface MviEffect

interface MviSuccessScreenState : MviScreenState

interface MviErrorScreenState : MviScreenState

interface MviLoadingScreenState : MviScreenState

inline fun <reified ScreenState : MviScreenState, reified SuccessState : MviSuccessScreenState> ScreenState.updateSuccessState(
    reducer: SuccessState.() -> ScreenState
): ScreenState = when(this) {
    is SuccessState -> reducer()
    else -> this
}

inline fun <reified ScreenState : MviScreenState, reified ErrorState : MviErrorScreenState> ScreenState.updateErrorState(
    reducer: ErrorState.() -> ScreenState
): MviScreenState = when(this) {
    is ErrorState -> reducer()
    else -> this
}

inline fun <reified ScreenState : MviScreenState, reified LoadingState : MviLoadingScreenState> ScreenState.updateLoadingState(
    reducer: LoadingState.() -> ScreenState
): MviScreenState = when(this) {
    is LoadingState -> reducer()
    else -> this
}
