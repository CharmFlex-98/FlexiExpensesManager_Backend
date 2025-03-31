package com.charmflex.fem.auth.domain.model

import com.charmflex.fem.auth.domain.exception.DeviceException


internal data class Device(
    val deviceId: String,
    val brand: String,
    val model: String,
    val sdkVersion: Int,
    val androidVersion: String
) {
    init {
        require(deviceId.isNotBlank()) {
            throw DeviceException.DeviceInvalidParamsException("deviceId", "deviceId is blank!")
        }
    }
}