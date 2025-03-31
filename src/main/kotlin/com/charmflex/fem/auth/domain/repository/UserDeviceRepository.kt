package com.charmflex.fem.auth.domain.repository

internal interface UserDeviceRepository {
    fun save(userId: String, deviceId: String)
}