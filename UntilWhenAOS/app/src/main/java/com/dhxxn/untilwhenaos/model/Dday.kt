package com.dhxxn.untilwhenaos.model

import java.util.*

data class Dday(
    var id : Int = 0,
    var finishDate : Date? = null,
    var startDate : Date? = null,
    var userId : String? = "",
    var content : String? = ""
)
