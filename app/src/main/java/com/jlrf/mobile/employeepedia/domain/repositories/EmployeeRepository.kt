package com.jlrf.mobile.employeepedia.domain.repositories

import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel

interface EmployeeRepository {

    suspend fun getEmployees() : List<EmployeeModel>

    suspend fun getEmployeeDetails(id: Long) : EmployeeModel?
}