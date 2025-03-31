package com.charmflex.fem.auth.application.model

internal data class LoginUserCommand(
    val uid: String,
    val username: String?,
    val email: String?,
    val deviceInfo: DeviceInfoDTO?,
) {
    data class DeviceInfoDTO(
        val deviceId: String,
        val brand: String,
        val model: String,
        val sdkVersion: Int,
        val androidVersion: String
    )
}