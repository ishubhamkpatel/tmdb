package dev.shubhampatel.tmdb.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

sealed interface BaseContract {
    interface ViewState
    interface ViewEvent
    interface ViewSideEffect
}

abstract class BaseUseCase<Event : BaseContract.ViewEvent, UiState : BaseContract.ViewState,
        Effect : BaseContract.ViewSideEffect> {

    private val initialState: UiState by lazy { setInitialState() }
    abstract fun setInitialState(): UiState

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected suspend fun subscribeToEvents() {
        _event.collect {
            handleEvents(it)
        }
    }

    abstract suspend fun handleEvents(event: Event)

    protected suspend fun setEvent(event: Event) {
        _event.emit(event)
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected suspend fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        _effect.send(effectValue)
    }
}