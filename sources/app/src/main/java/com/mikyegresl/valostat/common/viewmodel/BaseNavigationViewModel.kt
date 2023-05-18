package com.mikyegresl.valostat.common.viewmodel

import com.mikyegresl.valostat.common.state.BaseState
import kotlinx.coroutines.CoroutineScope

abstract class BaseNavigationViewModel<T : BaseState>(
    coroutineScope: CoroutineScope? = null
) : BaseStateViewModel<T>(coroutineScope)
