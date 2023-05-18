package com.mikyegresl.valostat.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun ViewModel.getBackgroundDispatcher(coroutineScope: CoroutineScope?) =
    coroutineScope?.coroutineContext ?: (this.viewModelScope.coroutineContext + Dispatchers.IO)
