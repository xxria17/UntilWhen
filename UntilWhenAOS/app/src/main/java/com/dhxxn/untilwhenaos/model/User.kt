package com.dhxxn.untilwhenaos.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    var id : Int = 0,
    var name : String? = "",
    var password : String? = ""
)
