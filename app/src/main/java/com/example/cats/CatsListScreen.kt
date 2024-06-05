package com.example.cats

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.core.mvi.MviScreen
import com.example.core.mvi.MviViewModel
import com.example.core.utils.textChanges
import com.example.core.utils.viewBinding
import com.example.di.DaggerFeatureCatsComponent
import com.example.di.DaggerFeatureDogsComponent
import com.example.di.FeatureCatsModuleDependencies
import com.example.di.FeatureDogsModuleDependencies

import com.example.dogs.DogsListViewModel
import com.example.dogs.DogsListsAdapter
import com.example.vm.databinding.ScreenCatsBinding
import com.example.vm.databinding.ScreenDogsBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class CatsListScreen : MviScreen<CatsListContract.State, CatsListContract.State.ScreenState, CatsListContract.State.ViewModelState, CatsListContract.Event, CatsListContract.Effect>() {

    override val binding by viewBinding<ScreenCatsBinding>()
    override val viewModel: MviViewModel<CatsListContract.State, CatsListContract.State.ScreenState, CatsListContract.State.ViewModelState, CatsListContract.Event, CatsListContract.Effect> by viewModels<CatsListViewModel>()
    private val catsAdapter = CatsListsAdapter()

    override fun injectDependency() {
        DaggerFeatureCatsComponent.builder()
            .context(requireActivity().applicationContext)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context = requireActivity().applicationContext,
                    entryPoint = FeatureCatsModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun invalidateState(state: CatsListContract.State.ScreenState) {
        binding.apply {
            progressCircular.isVisible = state is CatsListContract.State.ScreenState.Loading
            rvCats.isVisible = state is CatsListContract.State.ScreenState.Success
            when (state) {
                is CatsListContract.State.ScreenState.Loading -> Unit
                is CatsListContract.State.ScreenState.Error -> Unit
                is CatsListContract.State.ScreenState.Success -> catsAdapter.submitList(state.data)
            }
        }
    }

    override fun invalidateEffect(effect: CatsListContract.Effect) = Unit

    @OptIn(FlowPreview::class)
    override fun setupView() {
        binding.apply {
            rvCats.adapter = catsAdapter

            editText.textChanges()
                .distinctUntilChanged()
                .debounce(500)
                .listenUpdates { viewModel.setEvent(CatsListContract.Event.Search(it)) }
        }
    }
}