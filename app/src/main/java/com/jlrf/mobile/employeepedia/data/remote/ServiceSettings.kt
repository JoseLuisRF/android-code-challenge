package com.jlrf.mobile.employeepedia.data.remote

interface ServiceSettings {

    fun getAuthorizationHeader(): Map<String, String>
}