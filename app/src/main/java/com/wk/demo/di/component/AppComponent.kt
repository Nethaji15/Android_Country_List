package com.wk.demo.di.component

import com.wk.demo.App
import com.wk.demo.di.module.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * class to define the components that are provided for the app with the defined list of modules
 */
@Singleton
@Component(
    modules = [(AndroidInjectionModule::class),(AppModule::class),(NetModule::class),(RetrofitModule::class),(ActivityBuldersModule::class)]
)
interface AppComponent {
    fun inject(app: App)

}