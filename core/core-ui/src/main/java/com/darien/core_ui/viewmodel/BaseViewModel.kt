package com.darien.core_ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darien.core_ui.data.Action
import com.darien.core_ui.data.Effect
import com.darien.core_ui.data.State
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

open class BaseViewModel<S : State, A : Action, E : Effect>(
    initialState: S,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
    val uiEffect = _effect.asSharedFlow()

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, ex ->
            handleError(ex)
        }
    }

    private val viewModelSafeScope by lazy {
        viewModelScope + dispatcher + coroutineExceptionHandler
    }

    open fun handleError(exception: Throwable) {
        Log.e(
            "BaseViewModel",
            "CoroutineExceptionHandler: ${exception.localizedMessage}",
            exception
        )
    }

    fun submitAction(action: A) {
        viewModelSafeScope.launch {
            handleAction(action)
        }
    }

    protected open suspend fun handleAction(action: A) {}
}