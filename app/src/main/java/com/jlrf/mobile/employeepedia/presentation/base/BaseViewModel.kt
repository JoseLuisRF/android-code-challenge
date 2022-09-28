package com.jlrf.mobile.employeepedia.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface BaseState
interface BaseAction

typealias Reducer <S, A> = (S, A) -> S

@Suppress("EXPERIMENTAL_API_USAGE")
abstract class BaseViewModel<S : BaseState, A : BaseAction>(
    val initialState: S,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val actions = Channel<A>()

    private val store = MutableStateFlow(initialState)

    private var isEnabled: Boolean = true

    protected abstract fun reduce(oldState: S, action: A): S

    val state = store
        .asStateFlow()

    // Helper property to get a non-null state object
    open val currentState: S
        get() = state.value

    init {
        viewModelScope.launch(dispatcherProvider.main()) {
            while (isEnabled && !actions.isClosedForReceive) {
                try {
                    store.emit(reduce(store.value, actions.receive()))
                } catch (throwable: Throwable) {
                    Log.e(this.javaClass.simpleName, throwable.message.toString())
                }
            }
        }
    }

    fun dispatch(action: A) {
        viewModelScope.launch(dispatcherProvider.io()) {
            actions.send(action)
        }
    }

    override fun onCleared() {
        super.onCleared()
        isEnabled = false
        actions.close()
    }
}