package com.jlrf.mobile.employeepedia.domain.base

sealed class CommonError : Error() {
    data class GenericError(val throwable: Throwable, val errorCode: Int = -1) : CommonError()
    object ConnectionError : CommonError()
    object UnauthorisedError : CommonError()
    object CancelledError : CommonError()
}
