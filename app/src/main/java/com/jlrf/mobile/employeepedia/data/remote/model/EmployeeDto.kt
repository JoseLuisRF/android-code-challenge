package com.jlrf.mobile.employeepedia.data.remote.model

import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("employee_name")
    val employeeName: String? = null,

    @SerializedName("employee_salary")
    val employeeSalary: Double? = null,

    @SerializedName("employee_age")
    val employeeAge: Int? = null,

    @SerializedName("profile_image")
    val profileImage: String? = null
)
