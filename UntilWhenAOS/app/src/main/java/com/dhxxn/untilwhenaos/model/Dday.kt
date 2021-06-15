package com.dhxxn.untilwhenaos.model

data class Dday(
    var id : Int = 0,
    var finishDate : String? = null,
    var startDate : String? = null,
    var content : String? = "",
    var totalRemainDates : Long = 0
)
