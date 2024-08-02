package com.yongdd.core.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongdd.core.common.resource.StringValue
import com.yongdd.core.ui.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

interface ViewState
interface ViewEvent
interface ViewSideEffect

sealed class LoadState {
    data object Idle : LoadState()
    data class Loading(val message : String? = null) : LoadState()
    data class ErrorDialog(
        val title : StringValue? = null,
        val message : StringValue? = null,
        val errorMessage : StringValue? = null,
        val confirmText: StringValue = StringValue.Empty,
        val dismissText: StringValue? = StringValue.Empty,
        val onConfirm : () -> Unit = {},
        val onDismiss : () -> Unit = {}
    ) : LoadState() {
        constructor(
            title: String? = null,
            message: String? = null,
            errorMessage: String? = null,
            confirmText: String = "",
            dismissText: String? = "",
            onConfirm: () -> Unit = {},
            onDismiss: () -> Unit = {}
        ) : this(
            title = title?.let { StringValue.DynamicString(title) },
            message = message?.let { StringValue.DynamicString(message) },
            errorMessage = errorMessage?.let { StringValue.DynamicString(errorMessage) },
            confirmText = StringValue.DynamicString(confirmText),
            dismissText = dismissText?.let { StringValue.DynamicString(dismissText) },
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
    data class AlarmDialog(
        val title : StringValue? = null,
        val message : StringValue? = null,
        val confirmText : StringValue? = StringValue.Empty,
        val dismissText : StringValue? = StringValue.Empty,
        val onConfirm : () -> Unit = {},
        val onDismiss : () -> Unit = {}
    ) : LoadState() {
        constructor(
            title: String? = null,
            message: String? = null,
            confirmText: String? = "",
            dismissText: String? = "",
            onConfirm: () -> Unit = {},
            onDismiss: () -> Unit = {}
        ) : this(
            title = title?.let { StringValue.DynamicString(title) },
            message = message?.let { StringValue.DynamicString(message) },
            confirmText = confirmText?.let{ StringValue.DynamicString(confirmText)},
            dismissText = dismissText?.let { StringValue.DynamicString(dismissText) },
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}

sealed class CommonEvent : ViewEvent {
    /**
     * Life Cycle
     */
    data object OnCreate : CommonEvent()
    data object OnResume : CommonEvent()
    data object OnPause : CommonEvent()
    data object OnDestroy : CommonEvent()

    /**
     * etc
     * */
    data object CloseEvent : CommonEvent()
    data object ApplicationExitEvent : CommonEvent()
}

sealed class CommonEffect : ViewSideEffect {
    sealed class Navigation : CommonEffect() {
        data object NavigateMain : Navigation()
        data object NavigateBack : Navigation()
        data object NavigateApplicationExit : Navigation()
    }
}

const val DEFAULT_DELAY_TIME = 15000L
const val COMMON_EFFECTS_KEY = "common_effects_key"
const val SIDE_EFFECTS_KEY = "side_effects_key"

abstract class BaseViewModel<UiState : ViewState, Event : ViewEvent, Effect : ViewSideEffect> : ViewModel() {
    private val rootContext : CoroutineContext
        get() = viewModelScope.coroutineContext + CoroutineExceptionHandler { _, throwable ->
            sendError(throwable)
        }
    protected val scope = CoroutineScope(rootContext)

    abstract fun setInitialState() : UiState
    abstract fun handleEvents(event : Event)
    open fun handleCommonEvents(event : CommonEvent) {
        when(event) {
            is CommonEvent.OnCreate -> {}
            is CommonEvent.OnResume -> {}
            is CommonEvent.OnPause -> {}
            is CommonEvent.OnDestroy -> {}
            is CommonEvent.CloseEvent -> {}
            is CommonEvent.ApplicationExitEvent -> {}
        }
    }

    private val initialState : UiState by lazy { setInitialState() }
    /**
     * _uiState : Screen에 전달할 데이터
     * _loadState : Screen에 dialog로 호출되는 공통 요소의 상태
     * _event : Screen 으로부터 전달 받을 이벤트
     * _effect : Screen 에 전달할 이펙트
     */
    private val _uiState : MutableState<UiState> = mutableStateOf(initialState)
    private val _loadState : MutableState<LoadState> = mutableStateOf(LoadState.Idle)
    private val _event : MutableSharedFlow<Event> = MutableSharedFlow()
    private val _effect : Channel<Effect> = Channel()

    private val _commonEvent : MutableSharedFlow<CommonEvent> = MutableSharedFlow()
    private val _commonEffect : Channel<CommonEffect> = Channel()

    val uiState : State<UiState> = _uiState
    val loadState : State<LoadState> = _loadState
    val effect = _effect.receiveAsFlow().shareIn(scope = scope, started = SharingStarted.WhileSubscribed())

    val commonEffect = _commonEffect.receiveAsFlow()

    /**
     * Event는 ViewModel 에서 수집하여 처리한다
     */
    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        scope.launch {
            _commonEvent.collect {
                handleCommonEvents(it)
            }
        }

        scope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    /**
     * UiState, Event, Effect의 Setter
     */
    protected fun setState(reducer : UiState.() -> UiState) {
        val newState = uiState.value.reducer()
        _uiState.value = newState
    }

    protected fun setLoadState(loadState : LoadState) {
        _loadState.value = loadState
    }
    private fun setLoadStateIdle() {
        _loadState.value = LoadState.Idle
    }

    fun setEvent(event : Event) {
        scope.launch { _event.emit(event) }
    }

    fun setCommonEvent(event : CommonEvent) {
        scope.launch { _commonEvent.emit(event) }
    }

    protected fun setEffect(builder : () -> Effect) {
        scope.launch { _effect.send(builder()) }
    }

    protected fun setCommonEffect(builder : () -> CommonEffect) {
        scope.launch { _commonEffect.send(builder()) }
    }

    /**
     * 로딩
     */
    fun launchWithLoading(
        context : CoroutineContext = Dispatchers.IO,
        start : CoroutineStart = CoroutineStart.DEFAULT,
        delayTime : Long = DEFAULT_DELAY_TIME,
        endLoadState: LoadState? = LoadState.Idle,
        block : suspend CoroutineScope.() -> Unit
    ) : Job {
        return scope.launch(context, start) {
            withContext(Dispatchers.Main) {
                _loadState.value = LoadState.Loading()
                withContext(context = context) {
                    withTimeout(delayTime) {
                        block.invoke(this)
                    }
                }
                endLoadState?.let {
                    _loadState.value = endLoadState
                }
            }
        }.apply {
            invokeOnCompletion { cause : Throwable? ->
                cause?.also { cancelException ->
                    if(cancelException.cause != null){
                        sendError(cancelException.cause!!)
                    }else{
                        sendError(cancelException)
                    }
                }
            }
        }
    }

    /**
     * Exception 추가
     */
    fun launchWithException(
        context : CoroutineContext = Dispatchers.IO,
        start : CoroutineStart = CoroutineStart.DEFAULT,
        delayTime : Long = DEFAULT_DELAY_TIME,
        block : suspend CoroutineScope.() -> Unit
    ) : Job {
        return scope.launch(context, start) {
            withContext(Dispatchers.Main) {
                withContext(context = context) {
                    withTimeout(delayTime) {
                        block.invoke(this)
                    }
                }
            }
        }.apply {
            invokeOnCompletion { cause : Throwable? ->
                cause?.also { cancelException ->
                    if(cancelException.cause != null){
                        sendError(cancelException.cause!!)
                    }else{
                        sendError(cancelException)
                    }
                }
            }
        }
    }

    fun launchWithExceptionNotTimeOut(
        context : CoroutineContext = Dispatchers.IO,
        start : CoroutineStart = CoroutineStart.DEFAULT,
        block : suspend CoroutineScope.() -> Unit
    ) : Job {
        return scope.launch(context, start) {
            withContext(Dispatchers.Main) {
                withContext(context = context) {
                    block.invoke(this)
                }
            }
        }.apply {
            invokeOnCompletion { cause : Throwable? ->
                cause?.also { cancelException ->
                    if(cancelException.cause != null){
                        sendError(cancelException.cause!!)
                    }else{
                        sendError(cancelException)
                    }
                }
            }
        }
    }

    /**
     * 에러 처리
     */
    protected fun sendError(throwable: Throwable) {
        showErrorResult(throwable)
    }

    protected open fun showErrorResult(
        throwable: Throwable,
        defaultConfirm : ()-> Unit = { setCommonEvent (CommonEvent.CloseEvent)  },
        defaultDismiss : ()-> Unit = ::setLoadStateIdle
    ){
        _loadState.value = when(throwable){

            is CancellationException -> LoadState.ErrorDialog(
                message = StringValue.StringResource(R.string.basicErrorMessage),
                errorMessage = StringValue.DynamicString(throwable.message?:"Unknown CancellationException"),
                dismissText = null,
                onConfirm = defaultConfirm
            )

            // 인터넷 연결 오류
            is IOException -> LoadState.ErrorDialog(
                message = StringValue.StringResource(R.string.basicErrorMessage),
                dismissText = null,
                onConfirm = defaultConfirm
            )

            else -> LoadState.ErrorDialog(
                message = StringValue.StringResource(R.string.basicErrorMessage),
                errorMessage = StringValue.DynamicString(throwable.message?:throwable::class.java.simpleName),
                dismissText = null,
                onConfirm = defaultConfirm
            )
        }
    }
}