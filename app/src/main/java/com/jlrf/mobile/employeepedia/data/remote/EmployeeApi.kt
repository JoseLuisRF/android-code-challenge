package com.jlrf.mobile.employeepedia.data.remote

import com.jlrf.mobile.employeepedia.data.remote.model.EmployeeDto
import java.net.HttpURLConnection
import javax.inject.Inject

class EmployeeApi @Inject constructor(private val service: EmployeeService) {

    suspend fun getEmployees() : List<EmployeeDto>? {
        val response = service.getEmployees()
        return response.code()
            .takeIf { it == HttpURLConnection.HTTP_OK }
            ?.let { response.body()?.data }
    }

    suspend fun getEmployeeDetails(id: Long) : EmployeeDto? {
        val response = service.getEmployee(id)
        return response.code()
            .takeIf { it == HttpURLConnection.HTTP_OK }
            ?.let { response.body()?.data }
    }
}