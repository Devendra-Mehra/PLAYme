package com.playme.core

import android.content.SharedPreferences
import com.playme.utils.Constant.BOOKMARKED
import javax.inject.Inject


class PersistenceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PersistenceContract {
    override fun getBookMarked(): Set<String> {
        return sharedPreferences.getStringSet(BOOKMARKED, null) ?: emptySet()
    }

    override fun storeBookMark(bookMark: String) {
        with(sharedPreferences.edit()) {
            val set: Set<String> = getBookMarked()
            putStringSet(BOOKMARKED, set.plus(bookMark))
            apply()
        }

    }

    override fun removeBookMark(bookMark: String) {
        with(sharedPreferences.edit()) {
            val set: Set<String> = getBookMarked().toMutableSet()
            putStringSet(BOOKMARKED, set.minus(bookMark))
            apply()
        }
    }
}