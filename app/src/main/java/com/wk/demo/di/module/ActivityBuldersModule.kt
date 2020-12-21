package com.wk.demo.di.module


import com.wk.demo.ui.home.CountrieItemFragment
import com.wk.demo.ui.home.CountriesListFragment
import com.wk.demo.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * class to provide the injection for the activities
 */
@Module
abstract class ActivityBuldersModule {


    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeCountriesList(): CountriesListFragment

    @ContributesAndroidInjector
    abstract fun contributeCountrieItemFragment(): CountrieItemFragment


}