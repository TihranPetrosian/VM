package com.example.cats

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.MviViewModel
import com.example.core.mvi.updateSuccessState
import com.example.vm.share_domain.model.cats.CatsVo
import com.example.vm.share_domain.use_case.fetch_cats.FetchCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsListViewModel @Inject constructor(
    private val fetchCatsUseCase: FetchCatsUseCase,
) : MviViewModel<CatsListContract.State, CatsListContract.State.ScreenState, CatsListContract.State.ViewModelState, CatsListContract.Event, CatsListContract.Effect>(
    initialState = CatsListContract.State(
        viewModelState = CatsListContract.State.ViewModelState(emptyList()),
        screenState = CatsListContract.State.ScreenState.Loading,
    )
) {

    init {
        loadData()
    }

    override fun handleEvent(event: CatsListContract.Event) {
        when (event) {
            is CatsListContract.Event.Search -> handleSearchDogsEvent(event.query)
        }
    }

    private fun loadData() {
        fetchCatsUseCase.execute()
            .onStart { setLoadingState() }
            .onEach(::handleResult)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    private fun setLoadingState() = setState {
        copy(
            screenState = CatsListContract.State.ScreenState.Loading,
        )
    }

    private fun handleResult(data: List<CatsVo>) = setState {
        copy(
            viewModelState = viewModelState.copy(
                cats = data,
            ),
            screenState = CatsListContract.State.ScreenState.Success(
                data = data,
            )
        )
    }

    private fun handleError(throwable: Throwable) = setState {
        copy(
            screenState = CatsListContract.State.ScreenState.Error,
        )
    }

    private fun handleSearchDogsEvent(query: String) {
        viewModelScope.launch {
            setState {
                copy(
                    screenState = screenState.updateSuccessState<CatsListContract.State.ScreenState, CatsListContract.State.ScreenState.Success> {
                        copy(
                            data = viewModelState.cats.filter {
                                it.name?.contains(query) == true || it.bredFor?.contains(query) == true
                            }
                        )
                    }
                )
            }
        }
    }
}
