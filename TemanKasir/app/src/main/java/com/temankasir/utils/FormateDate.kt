package com.temankasir.utils

import android.util.Log
import java.text.SimpleDateFormat

class FormateDate {

    fun formatDateID(simpleDateTime : String) : String {
        var dateID = ""

        var patternDay = "dd"
        var patternMonth = "MMMM"
        var patternYear = "yyyy"
        var simpleDateFormatDay : SimpleDateFormat = SimpleDateFormat(patternDay)
        var simpleDateFormatMonth : SimpleDateFormat = SimpleDateFormat(patternMonth)
        var simpleDateFormatYear : SimpleDateFormat = SimpleDateFormat(patternYear)

        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")

        var dayJoinFormat = simpleDateFormatDay.format(tanggalFromAPI.parse(simpleDateTime))
        var monthJoinFormat = simpleDateFormatMonth.format(tanggalFromAPI.parse(simpleDateTime))
        var yearJoinFormat = simpleDateFormatYear.format(tanggalFromAPI.parse(simpleDateTime))

        dateID = dayJoinFormat + " " + monthJoinFormat + " " + yearJoinFormat

        Log.i("DATEID", " === " + dateID)

        return dateID
    }
}