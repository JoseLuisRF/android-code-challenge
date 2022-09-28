package com.jlrf.mobile.employeepedia.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetEmployeeDetailsResponse(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: EmployeeDto? = null
)