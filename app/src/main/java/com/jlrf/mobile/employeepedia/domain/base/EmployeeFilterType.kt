package com.jlrf.mobile.employeepedia.domain.base

sealed class EmployeeFilterType(val value: String) {
    object Salary : EmployeeFilterType("Salary")
    object Age : EmployeeFilterType("Age")
    object None : EmployeeFilterType("None")
}