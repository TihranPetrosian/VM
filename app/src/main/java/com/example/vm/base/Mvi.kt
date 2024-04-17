package com.example.vm.base

abstract class MviState {
    abstract val viewModelState: MviViewModelState
    abstract val screenState: MviScreenState
}

interface MviViewModelState

interface MviScreenState

interface MviEvent

interface MviEffect
