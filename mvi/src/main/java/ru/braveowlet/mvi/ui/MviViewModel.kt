package ru.braveowlet.mvi.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel <Event : IViewEvent, UiState : IViewState, Effect : IViewEffect> : ViewModel() {

    //State
    private val initialState: UiState by lazy { setInitialState() }
    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState
    abstract fun setInitialState(): UiState
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }


    //Event
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }
    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect { handleEvents(it) }
        }
    }
    abstract fun handleEvents(event: Event)


    //Effect
    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }


    init { subscribeToEvents() }
}