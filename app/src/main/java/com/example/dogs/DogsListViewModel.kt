package com.example.dogs

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.MviViewModel
import com.example.core.mvi.updateSuccessState
import com.example.vm.share_domain.state.Resource
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.dogs.DogsListContract.State.ScreenState
import com.example.dogs.DogsListContract.State.ViewModelState
import com.example.dogs.DogsListContract.State
import com.example.dogs.DogsListContract.Event
import com.example.dogs.DogsListContract.Effect

@HiltViewModel
class DogsListViewModel @Inject constructor(
    private val fetchDogsUseCase: FetchDogsUseCase,
) : MviViewModel<State, ScreenState, ViewModelState, Event, Effect>(
    initialState = State(
        viewModelState = ViewModelState(emptyList()),
        screenState = ScreenState.Loading,
    )
) {

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.Search -> handleSearchDogsEvent(event.query)
        }
    }

    private fun loadData() {
        fetchDogsUseCase.execute()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> setState {
                        copy(
                            screenState = ScreenState.Loading,
                        )
                    }
                    is Resource.Success -> setState {
                        copy(
                            viewModelState = viewModelState.copy(
                                dogs = resource.data,
                            ),
                            screenState = ScreenState.Success(
                                data = resource.data,
                            )
                        )
                    }
                    is Resource.Error -> setState {
                        copy(
                            screenState = ScreenState.Error,
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
                    screenState = screenState.updateSuccessState<ScreenState, ScreenState.Success> {
                        copy(
                            data = viewModelState.dogs.filter {
                                it.name?.contains(query) == true || it.bredFor?.contains(query) == true
                            }
                        )
                    }
                )
            }
        }
    }
}
