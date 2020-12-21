package com.wk.demo.data.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "countries")
class Countries(
    @PrimaryKey
    @field:Json(name = "_id")
    var _id: String = "",
    @field:Json(name = "countriesList")
    var countriesList: String = ""
)