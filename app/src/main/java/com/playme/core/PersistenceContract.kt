package com.playme.core

interface PersistenceContract {
    fun getBookMarked(): Set<String>

    fun storeBookMark(bookMark: String)

    fun removeBookMark(bookMark: String)
}