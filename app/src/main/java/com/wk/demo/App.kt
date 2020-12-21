package com.wk.demo

import android.app.Activity
import android.app.Application
import androidx.work.*

import com.wk.demo.di.component.AppComponent
import com.wk.demo.di.component.DaggerAppComponent
import com.wk.demo.di.module.AppModule
import com.wk.demo.di.module.NetModule
import com.wk.demo.di.module.RetrofitModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject



/**
 * Application class to initialize sdk's and other lib/service that were supposed to be initialize on application class
 */


open class App : Application(), HasAndroidInjector {

    companion object {
        private lateinit var instance: App

    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()


        // Create charging constraint
         Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        getComponent().inject(this)

    }
    private var applicationComponent: AppComponent? = null

    private fun getComponent(): AppComponent {

        if (applicationComponent == null) {
            applicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this)).netModule(NetModule()).retrofitModule(RetrofitModule())
                .build()
        }
        return applicationComponent!!
    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector as AndroidInjector<Any>

}