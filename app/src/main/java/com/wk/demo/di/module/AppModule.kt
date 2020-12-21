package com.wk.demo.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.wk.demo.data.model.room.AppDatabase
import com.wk.demo.data.model.room.dao.CountriesDAO
import com.wk.demo.utils.Constants
import com.wk.demo.utils.PrefKeys
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
/**
 * class to provide injection for application level access
 */
@Module
class AppModule (val app:Application){
    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase = Room.databaseBuilder(app,
        AppDatabase::class.java, Constants.DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providecountriesDAO(
        database: AppDatabase
    ): CountriesDAO= database.countriesDAO()

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application):SharedPreferences = app.getSharedPreferences(PrefKeys.PREFNAME, Context.MODE_PRIVATE)

}