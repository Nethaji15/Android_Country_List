package com.wk.demo.ui.splash

import androidx.lifecycle.ViewModel
import com.wk.demo.ui.base.BaseViewModelFactory
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate SplashViewModel.
 * Required given SplashViewModel has a non-empty constructor
 */
class SplashViewModelFactory @Inject constructor(private val splashViewModel: SplashViewModel): BaseViewModelFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return splashViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
