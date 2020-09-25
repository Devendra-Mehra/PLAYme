package com.playme.core

import android.content.SharedPreferences
import javax.inject.Inject

const val BOOKMARKED = "BOOKMARKED"

class PersistenceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PersistenceContract {
    override fun getBookMarked(): Set<String>? {
        return sharedPreferences.getStringSet(BOOKMARKED, null)
    }

    override fun storeBookMark(bookMark: String) {
        with(sharedPreferences.edit()) {
            val set: Set<String> = HashSet()
            putStringSet(BOOKMARKED, set.plus(bookMark))
            apply()
        }

    }

    override fun removeBookMark(bookMark: String) {
        with(sharedPreferences.edit()) {
            remove(bookMark)
            apply()
        }
    }
}