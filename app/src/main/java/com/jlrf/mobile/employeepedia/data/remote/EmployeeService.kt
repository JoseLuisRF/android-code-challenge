package com.jlrf.mobile.employeepedia.data.remote

import com.jlrf.mobile.employeepedia.data.remote.model.GetEmployeeDetailsResponse
import com.jlrf.mobile.employeepedia.data.remote.model.GetEmployeesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeService {

    @GET("/api/v1/employees")
    suspend fun getEmployees(): Response<GetEmployeesResponse>

    @GET("/api/v1/employee/{$employeeId}")
    suspend fun getEmployee(@Path(Companion.employeeId) employeeId: Long): Response<GetEmployeeDetailsResponse>

    companion object {
        const val employeeId = "employeeId"
    }
}