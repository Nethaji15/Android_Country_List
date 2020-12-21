package com.wk.demo.data.remote.reqres

import com.squareup.moshi.Json

data class CountriesItem(
    @Json(name = "alpha2Code")
    val alpha2Code: String?=null,
    @Json(name = "alpha3Code")
    val alpha3Code: String?=null,
    @Json(name = "altSpellings")
    val altSpellings: List<String>?=null,
    @Json(name = "area")
    val area: Double?=null,
    @Json(name = "borders")
    val borders: List<String>?=null,
    @Json(name = "callingCodes")
    val callingCodes: List<String>?=null,
    @Json(name = "capital")
    val capital: String?=null,
    @Json(name = "cioc")
    val cioc: String?=null,
    @Json(name = "currencies")
    val currencies: List<Currency>?=null,
    @Json(name = "demonym")
    val demonym: String?=null,
    @Json(name = "flag")
    val flag: String?=null,
    @Json(name = "gini")
    val gini: Double?=null,
    @Json(name = "languages")
    val languages: List<Language>?=null,
    @Json(name = "latlng")
    val latlng: List<Double>?=null,
    @Json(name = "name")
    val name: String?=null,
    @Json(name = "nativeName")
    val nativeName: String?=null,
    @Json(name = "numericCode")
    val numericCode: String?=null,
    @Json(name = "population")
    val population: Int?=null,
    @Json(name = "region")
    val region: String?=null,/*
    @Json(name = "regionalBlocs")
    val regionalBlocs: List<RegionalBloc>?=null,*/
    @Json(name = "subregion")
    val subregion: String?=null,
    @Json(name = "timezones")
    val timezones: List<String>?=null,
    @Json(name = "topLevelDomain")
    val topLevelDomain: List<String>/*?=null,
    @Json(name = "translations")
    val translations: Translations*/
)