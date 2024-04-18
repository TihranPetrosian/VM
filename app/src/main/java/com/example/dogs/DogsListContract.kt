package com.example.dogs

import com.example.core.mvi.MviEffect
import com.example.core.mvi.MviErrorScreenState
import com.example.core.mvi.MviEvent
import com.example.core.mvi.MviLoadingScreenState
import com.example.core.mvi.MviScreenState
import com.example.core.mvi.MviState
import com.example.core.mvi.MviSuccessScreenState
import com.example.core.mvi.MviViewModelState
import com.example.vm.share_domain.model.dogs.DogVo

object DogsListContract {

    sealed interface Event : MviEvent {
        data class Search(val query: String) : Event
    }

    sealed interface Effect : MviEffect {
        data class NavigateToDogDetails(val id: Long) : Effect
        data class ShowErrorBanner(val message: String) : Effect
    }

    data class State(
        override val screenState: ScreenState,
        override val viewModelState: ViewModelState,
    ) : MviState() {

        data class ViewModelState(
            val dogs: List<DogVo>,
        ) : MviViewModelState

        sealed interface ScreenState : MviScreenState {

            data object Loading : ScreenState, MviLoadingScreenState

            data class Success(val data: List<DogVo>) : ScreenState,
                MviSuccessScreenState

            data object Error : ScreenState, MviErrorScreenState
        }
    }
}
