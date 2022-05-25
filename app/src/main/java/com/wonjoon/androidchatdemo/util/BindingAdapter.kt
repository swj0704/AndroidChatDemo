package com.wonjoon.androidchatdemo.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:time")
    fun getDateTime(textView : TextView, time : Long?){
        val date = Date(time?:System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("a hh:mm")
        val text = dateFormat.format(date)
        textView.text = text
    }
}