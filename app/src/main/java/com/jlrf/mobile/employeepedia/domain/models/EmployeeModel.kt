package com.jlrf.mobile.employeepedia.domain.models

data class EmployeeModel(
    val id: Long,
    val name: String,
    val salary: Double,
    val age: Int,
    val profileImage: String
)