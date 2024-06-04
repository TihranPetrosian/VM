package com.example.dogs

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.core.mvi.MviScreen
import com.example.core.mvi.MviViewModel
import com.example.core.utils.textChanges
import com.example.core.utils.viewBinding
import com.example.di.DaggerFeatureDogsComponent
import com.example.di.FeatureDogsModuleDependencies
import com.example.dogs.DogsListContract.Effect
import com.example.dogs.DogsListContract.Event
import com.example.dogs.DogsListContract.State
import com.example.dogs.DogsListContract.State.ScreenState
import com.example.dogs.DogsListContract.State.ViewModelState
import com.example.vm.databinding.ScreenDogsBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class DogsListScreen : MviScreen<State, ScreenState, ViewModelState, Event, Effect>() {

    override val binding by viewBinding<ScreenDogsBinding>()
    override val viewModel: MviViewModel<State, ScreenState, ViewModelState, Event, Effect> by viewModels<DogsListViewModel>()
    private val dogsAdapter = DogsListsAdapter()

    override fun injectDependency() {
        DaggerFeatureDogsComponent.builder()
            .context(requireActivity().applicationContext)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context = requireActivity().applicationContext,
                    entryPoint = FeatureDogsModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun invalidateState(state: ScreenState) {
        binding.apply {
            progressCircular.isVisible = state is ScreenState.Loading
            rvDogs.isVisible = state is ScreenState.Success
            when (state) {
                is ScreenState.Loading -> Unit
                is ScreenState.Error -> Unit
                is ScreenState.Success -> dogsAdapter.submitList(state.data)
            }
        }
    }

    override fun invalidateEffect(effect: Effect) = Unit

    @OptIn(FlowPreview::class)
    override fun setupView() {
        binding.apply {
            rvDogs.adapter = dogsAdapter

            editText.textChanges()
                .distinctUntilChanged()
                .debounce(500)
                .listenUpdates { viewModel.setEvent(Event.Search(it)) }
        }
    }
}