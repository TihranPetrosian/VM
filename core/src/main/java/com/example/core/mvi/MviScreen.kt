package com.example.core.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class MviScreen<State : MviState, ScreenState : MviScreenState, ViewModelState: MviViewModelState, Event : MviEvent, Effect : MviEffect> : Fragment() {

    protected abstract val binding: ViewBinding
    protected abstract val viewModel: MviViewModel<State, ScreenState, ViewModelState, Event, Effect>

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        viewModel.apply {
            state.listenUpdates { invalidateState(it) }
            effect.listenUpdates { invalidateEffect(it) }
        }
    }

    open fun injectDependency() = Unit

    open fun setupView() = Unit

    protected abstract fun invalidateState(state: ScreenState)

    protected abstract fun invalidateEffect(effect: Effect)

    fun <T> Flow<T>.listenUpdates(invoker: (T) -> Unit) {
        onEach(invoker)
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}
