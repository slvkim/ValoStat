package com.mikyegresl.valostat.common.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.common.state.BaseState
import com.mikyegresl.valostat.common.state.GlobalViewState
import com.mikyegresl.valostat.base.error.NoInternetException
import com.mikyegresl.valostat.base.error.ValoStatException
import com.mikyegresl.valostat.base.network.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

abstract class BaseStateViewModel<T : BaseState>(
    coroutineScope: CoroutineScope? = null
) : ViewModel() {

    open val backgroundDispatcher: CoroutineContext = getBackgroundDispatcher(coroutineScope)

    protected abstract val _state: MutableStateFlow<T>
    val state: StateFlow<T> by lazy { _state }

    val currentState: T
        get() = _state.value

    private val _globalViewState = MutableStateFlow<GlobalViewState>(GlobalViewState.Content)
    val globalViewState: StateFlow<GlobalViewState> by lazy { _globalViewState }

    private val jobQueue: MutableList<Job> = mutableListOf()

    override fun onCleared() {
        jobQueue.forEach { if (it.isActive) it.cancel(COROUTINE_CLEARED_MSG) }
        super.onCleared()
    }

    protected fun doBackground(
        dispatcher: CoroutineContext = backgroundDispatcher,
        backgroundWork: suspend () -> Unit,
        onError: ((e: Throwable) -> Unit)? = null,
        loaderEnabled: Boolean = false,
    ) {
        viewModelScope.launch {
            if (loaderEnabled)
                setLoading()
            try {
                withContext(dispatcher) { backgroundWork() }
                setContent()
            } catch (e: Exception) {
                onError?.invoke(e)
                setError(e)
//                if (BuildConfig.DEBUG) {
//                    Log.e(ERROR_TAG, e.stackTraceToString())
//                }
            }
        }
    }

    protected fun <T> doBackground(
        flow: Flow<Response<T>>,
        onLoading: suspend () -> Unit = {},
        onSuccessLocal: suspend (T) -> Unit = {},
        onSuccessRemote: suspend (T) -> Unit = {},
        onError: suspend (Throwable) -> Boolean = { false }
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch { onError(throwable) }
        }

        val job = flow.onEach {
            when(it) {
                is Response.Loading -> onLoading()
                is Response.SuccessLocal -> it.data?.let { d -> onSuccessLocal(d) }
                is Response.SuccessRemote -> it.data?.let { d -> onSuccessRemote(d) }
                is Response.Error -> it.throwable?.let { t ->
                    if(onError(t).not()) //&& BuildConfig.DEBUG)
                        Log.e(ERROR_TAG, t.stackTraceToString())
                }
            }
        }.launchIn(viewModelScope + exceptionHandler)
        jobQueue.add(job)
        return job
    }

    fun updateState(value: T) {
        _state.value = value
    }

    fun updateState(
        dispatcher: CoroutineContext = backgroundDispatcher,
        calculateState: suspend () -> T,
        onErrorUpdate: ((e: Throwable) -> T)? = null,
        loaderEnabled: Boolean = false
    ) {
        doBackground(
            dispatcher = dispatcher,
            backgroundWork = { _state.value = calculateState() },
            onError = { error ->
                onErrorUpdate?.let { _state.value = onErrorUpdate(error) }
            },
            loaderEnabled = loaderEnabled
        )
    }

    private fun handleCoroutineError(throwable: Throwable) {
        setError(throwable)
//        if (BuildConfig.DEBUG) {
//            Log.e(ERROR_TAG, throwable.stackTraceToString())
//        }
    }

    private fun setLoading() {
        _globalViewState.value = GlobalViewState.Loading
    }

    private fun setContent() {
        _globalViewState.value = GlobalViewState.Content
    }

    private fun setError(error: Throwable) {
        val ex = (error as? ValoStatException) ?: ValoStatException(error)
        _globalViewState.value = GlobalViewState.Error(ex)
    }

    fun mapError(error: Throwable): Int =
        when (error) {
            is NoInternetException -> {
                R.string.connection_error_title
            }
            else -> R.string.general_error_title
        }

    private companion object {
        const val ERROR_TAG = "error tag"
        private const val COROUTINE_CLEARED_MSG = "Coroutine has been cleared due to ViewModel lifecycle finishing"
    }
}
