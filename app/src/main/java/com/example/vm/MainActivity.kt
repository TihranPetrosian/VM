package com.example.vm

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.vm.databinding.ActivityMainBinding
import com.example.vm.recycler.DogsListsAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val simpleViewModel: SimpleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rvDogs = binding.rvDogs
        val adapter = DogsListsAdapter()
        rvDogs.adapter = adapter

        lifecycleScope.launch {
            simpleViewModel.stateFlow.flowWithLifecycle(lifecycle).collectLatest {
                Log.d("Main", "onCreate: $it")
                adapter.submitList(it)
            }
        }
        binding.editText.textChanges()
            .distinctUntilChanged()
            .debounce(500)
            .onEach {
                simpleViewModel.searchDogs(it)
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