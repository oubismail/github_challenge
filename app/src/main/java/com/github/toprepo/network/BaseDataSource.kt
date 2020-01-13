package com.github.toprepo.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseDataSource {
    protected fun <T> getResult(call: suspend () -> Response<T>): LiveData<Result<T>> {
        val liveData = MutableLiveData<Result<T>>()
        CoroutineScope(Dispatchers.Main).launch {
            liveData.value = Result.loading()
            try {
                val response = call()
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        liveData.value = Result.success(data)
                    }
                } else {
                    liveData.value =
                        error(" ${response.code()} ${response.message()}", response.code())
                }
            } catch (e: Exception) {
                liveData.value = error(e.message ?: e.toString())
            } finally {
                liveData.value = Result.complete()
            }
        }
        return liveData
    }

    private fun <T> error(message: String, code: Int? = -1): Result<T> {
        return Result.error("Network call has failed for a following reason: $message", code = code)
    }
}

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class Result<out T>(val status: Status, val data: T?, val message: String?, val code: Int?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        COMPLETE
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> error(message: String, code: Int?, data: T? = null): Result<T> {
            return Result(
                Status.ERROR,
                data,
                message,
                code
            )
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(
                Status.LOADING,
                data,
                null,
                null
            )
        }

        fun <T> complete(): Result<T> {
            return Result(
                Status.COMPLETE,
                null,
                null,
                null
            )
        }
    }
}