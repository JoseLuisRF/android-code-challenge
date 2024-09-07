package com.jlrf.mobile.employeepedia.data

import com.jlrf.mobile.employeepedia.BuildConfig
import com.jlrf.mobile.employeepedia.data.remote.ServiceSettings
import javax.inject.Inject

class ServiceSettingsImpl @Inject constructor() : ServiceSettings {
    override fun getAuthorizationHeader(): Map<String, String> {
        return mapOf(
            "Authorization" to "Bearer ${BuildConfig.TMDB_TOKEN}",
        )
    }
}