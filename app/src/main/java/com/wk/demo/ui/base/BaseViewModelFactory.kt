package com.wk.demo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel provider factory to instantiate BaseViewModel.
 * Required given BaseViewModel has a non-empty constructor
 */
open class BaseViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            return BaseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
