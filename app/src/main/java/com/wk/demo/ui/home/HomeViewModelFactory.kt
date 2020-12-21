package com.wk.demo.ui.home

import androidx.lifecycle.ViewModel
import com.wk.demo.ui.base.BaseViewModelFactory
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate SplashViewModel.
 * Required given HomeViewModel has a non-empty constructor
 */
class HomeViewModelFactory @Inject constructor(private val homeViewModel: HomeViewModel): BaseViewModelFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return homeViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
