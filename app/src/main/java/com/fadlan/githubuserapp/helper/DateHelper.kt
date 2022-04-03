package com.fadlan.githubuserapp.helper

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private var dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    fun getCurrentDate(): String{
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getSimpleDateFormat(dateValue: String): String {
        val date = dateFormat.parse(dateValue)
        val simpleDate = SimpleDateFormat("dd MMM yyyy HH.mm", Locale.getDefault())
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return simpleDate.format(date)
    }

}