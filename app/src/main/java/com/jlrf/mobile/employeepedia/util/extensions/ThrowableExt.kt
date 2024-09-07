package com.jlrf.mobile.employeepedia.util.extensions

import com.jlrf.mobile.employeepedia.domain.base.CommonError
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.UnknownHostException

fun Throwable.toError(): Error {
    return when (this) {
        is CancellationException -> CommonError.CancelledError
        is ConnectException, is UnknownHostException -> CommonError.ConnectionError
        is HttpException -> if (code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            CommonError.UnauthorisedError
        } else {
            CommonError.GenericError(this, code())
        }
        else -> CommonError.GenericError(this)
    }
}