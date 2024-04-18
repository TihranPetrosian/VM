package com.example.core.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

fun EditText.textChanges(): Flow<String> = channelFlow {
    val textWatcher = addTextChangedListener {
        trySend(it?.toString().orEmpty())
    }
    awaitClose { removeTextChangedListener(textWatcher) }
}
