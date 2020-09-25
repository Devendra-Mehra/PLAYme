package com.playme.di

import android.content.SharedPreferences
import javax.inject.Inject

private const val BOOKMARKED = "BOOKMARKED"


class PreferenceHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getBookMarked(): Set<String>? =
        sharedPreferences.getStringSet(BOOKMARKED, null)


    fun storeBookMark(bookMark: String) {
        with(sharedPreferences.edit()) {
            val set: Set<String> = HashSet()
            putStringSet(BOOKMARKED, set.plus(bookMark))
            apply()
        }
    }

    fun removeBookMark(bookMark: String) {
        with(sharedPreferences.edit()) {
            remove(bookMark)
            apply()
        }
    }
}