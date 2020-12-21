package com.wk.demo.data.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wk.demo.data.model.room.entity.Countries
//Country DAO for CRUD operations
@Dao
interface CountriesDAO{
    @Query("SELECT countriesList from countries")
    fun getAll(): String

    @Query("DELETE from countries")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCountries(user: Countries):Long
}
