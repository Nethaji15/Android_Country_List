package com.wk.demo.utils

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.wk.demo.data.remote.reqres.CountriesItem


import javax.inject.Inject


/**
 * class to provide functions to access shared preference values
 */
class PrefUtils @Inject constructor() {

    @Inject
    lateinit var appPref: SharedPreferences

    @Inject
    lateinit var moshi: Moshi

    fun setCountriesItem(countriesItem: CountriesItem) {

        val adapter = moshi.adapter(CountriesItem::class.java).lenient()

        val editor = appPref.edit()
        val data = adapter.toJson(countriesItem)
        editor.putString(PrefKeys.COUNTRYDETAILS, data)
        editor.apply()
    }
    /**
     * Get countriesItem data from api to preference
     */
    fun getPrefUser(): CountriesItem? {
        val adapter = moshi.adapter(CountriesItem::class.java).lenient()
        val data = appPref.getString(PrefKeys.COUNTRYDETAILS, null)
        return if (data != null) {
            adapter.fromJson(data)
        } else
            null
    }

}
