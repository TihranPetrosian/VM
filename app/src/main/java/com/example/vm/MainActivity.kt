package com.example.vm

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.di.DaggerFeatureDogsComponent
import com.example.di.FeatureDogsModuleDependencies
import com.example.vm.databinding.ActivityMainBinding
import com.example.vm.recycler.DogsListsAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val simpleViewModel: SimpleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFeatureDogsComponent.builder()
            .context(applicationContext)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context = applicationContext,
                    entryPoint = FeatureDogsModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rvDogs = binding.rvDogs
        val adapter = DogsListsAdapter()
        rvDogs.adapter = adapter

        lifecycleScope.launch {
            simpleViewModel.listenState().flowWithLifecycle(lifecycle).collectLatest { screenState ->
                binding.apply {
                    progressCircular.isVisible = screenState is DogsListContract.State.ScreenState.Loading
                    rvDogs.isVisible = screenState is DogsListContract.State.ScreenState.Success
                    when (screenState) {
                        is DogsListContract.State.ScreenState.Loading -> Unit
                        is DogsListContract.State.ScreenState.Error -> Unit
                        is DogsListContract.State.ScreenState.Success -> adapter.submitList(screenState.data)
                    }
                }
            }
        }
        binding.editText.textChanges()
            .distinctUntilChanged()
            .debounce(500)
            .onEach {
                simpleViewModel.setEvent(DogsListContract.Event.Search(it))
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

}

fun EditText.textChanges(): Flow<String> = channelFlow {
    val textWatcher = addTextChangedListener {
        trySend(it?.toString().orEmpty())
    }
    awaitClose { removeTextChangedListener(textWatcher) }
}