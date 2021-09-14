package com.ozansan.sanews.util

sealed class Maybe<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : Maybe<T>(data, null)
    class Error<T>(message: String): Maybe<T>(null, message)
}