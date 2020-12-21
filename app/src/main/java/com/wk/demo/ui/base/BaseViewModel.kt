package com.wk.demo.ui.base

import androidx.lifecycle.ViewModel
import com.wk.demo.utils.PrefUtils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import javax.inject.Inject


/**
 * Base view model for common functionality
 */
open class BaseViewModel : ViewModel(),CoroutineScope {
    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = coroutineJob + Dispatchers.IO


    @Inject
    lateinit var prefUtils: PrefUtils



    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}
