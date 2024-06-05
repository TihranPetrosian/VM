package com.example.dogs

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.MviViewModel
import com.example.core.mvi.updateSuccessState
import com.example.dogs.DogsListContract.Effect
import com.example.dogs.DogsListContract.Event
import com.example.dogs.DogsListContract.State
import com.example.dogs.DogsListContract.State.ScreenState
import com.example.dogs.DogsListContract.State.ViewModelState
import com.example.vm.share_domain.model.dogs.DogsVo
import com.example.vm.share_domain.use_case.fetch_dogs.FetchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            .onStart { setLoadingState() }
            .onEach(::handleResult)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    private fun setLoadingState() = setState {
        copy(
            screenState = ScreenState.Loading,
        )
    }

    private fun handleResult(data: List<DogsVo>) = setState {
        copy(
            viewModelState = viewModelState.copy(
                dogs = data,
            ),
            screenState = ScreenState.Success(
                data = data,
            )
        )
    }

    private fun handleError(throwable: Throwable) = setState {
        copy(
            screenState = ScreenState.Error,
        )
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
