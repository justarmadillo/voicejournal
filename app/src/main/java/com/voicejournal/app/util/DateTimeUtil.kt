package com.voicejournal.app.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtil {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    fun formatDate(epochMs: Long): String = dateFormat.format(Date(epochMs))

    fun formatTime(epochMs: Long): String = timeFormat.format(Date(epochMs))

    fun formatDateTime(epochMs: Long): String {
        val date = Date(epochMs)
        return "${dateFormat.format(date)} at ${timeFormat.format(date)}"
    }

    fun formatMonthYear(epochMs: Long): String = monthYearFormat.format(Date(epochMs))

    fun getMonthYearKey(epochMs: Long): String {
        val cal = Calendar.getInstance().apply { timeInMillis = epochMs }
        return "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}"
    }

    fun now(): Long = System.currentTimeMillis()
}
