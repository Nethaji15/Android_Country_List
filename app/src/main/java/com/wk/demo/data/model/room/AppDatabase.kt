package com.wk.demo.data.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wk.demo.data.model.room.dao.CountriesDAO
import com.wk.demo.data.model.room.entity.Countries
//Room db creation
@Database(entities = [(Countries::class)],version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun countriesDAO():CountriesDAO

}