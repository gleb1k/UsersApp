package com.vk.usersapp.core.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        onException(throwable)
    }

    protected fun CoroutineScope.launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context + handler, block = block)

    protected open fun onException(exception: Throwable) {
        Log.e(TAG, "Exception handled", exception)
    }

    private companion object {
        const val TAG = "ViewModelTag"
    }
}