package com.example.vm

import androidx.lifecycle.viewModelScope
import com.example.vm.base.MviEffect
import com.example.vm.base.MviEvent
import com.example.vm.base.MviScreenState
import com.example.vm.base.MviState
import com.example.vm.base.MviViewModel
import com.example.vm.base.MviViewModelState
import com.example.vm.share_domain.model.dogs.DogVo
import com.example.vm.share_domain.state.Resource
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

object DogsListContract {

    sealed interface Event : MviEvent {
        data class Search(val query: String) : Event
    }

    sealed interface Effect : MviEffect

    data class State(
        override val screenState: ScreenState,
        override val viewModelState: ViewModelState,
    ) : MviState() {

        data class ViewModelState(
            val dogs: List<DogVo>,
        ) : MviViewModelState

        sealed interface ScreenState : MviScreenState {

            data object Loading : ScreenState

            data class Success(val data: List<DogVo>) : ScreenState

            data object Error : ScreenState
        }
    }
}

@HiltViewModel
class SimpleViewModel @Inject constructor(
    private val fetchDogsUseCase: FetchDogsUseCase,
) : MviViewModel<DogsListContract.State, DogsListContract.State.ScreenState, DogsListContract.State.ViewModelState, DogsListContract.Event, DogsListContract.Effect>(
    initialState = DogsListContract.State(
        viewModelState = DogsListContract.State.ViewModelState(emptyList()),
        screenState = DogsListContract.State.ScreenState.Loading,
    )
) {

    init {
        loadData()
    }

    override fun handleEvent(event: DogsListContract.Event) {
        when (event) {
            is DogsListContract.Event.Search -> handleSearchDogsEvent(event.query)
        }
    }

    private fun loadData() {
        fetchDogsUseCase.execute()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> setState {
                        copy(
                            screenState = DogsListContract.State.ScreenState.Loading,
                        )
                    }
                    is Resource.Success -> setState {
                        copy(
                            viewModelState = viewModelState.copy(
                                dogs = resource.data,
                            ),
                            screenState = DogsListContract.State.ScreenState.Success(
                                data = resource.data,
                            )
                        )
                    }
                    is Resource.Error -> setState {
                        copy(
                            screenState = DogsListContract.State.ScreenState.Error,
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleSearchDogsEvent(query: String) {
        viewModelScope.launch {
            setState {
                copy(
                    screenState = DogsListContract.State.ScreenState.Success(
                        data = viewModelState.dogs.filter {
                            it.name?.contains(query) == true || it.bredFor?.contains(query) == true
                        }
                    )
                )
            }
        }
    }
}