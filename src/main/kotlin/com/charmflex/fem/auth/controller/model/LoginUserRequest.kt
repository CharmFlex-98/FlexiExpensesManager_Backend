package com.charmflex.fem.auth.controller.model

import com.charmflex.fem.auth.application.model.LoginUserCommand

internal data class LoginUserRequest(
    val uid: String,
    val username: String?,
    val email: String?,
    val deviceInfo: UserDeviceInfo?
) {
    data class UserDeviceInfo(
        val deviceId: String,
        val brand: String,
        val model: String,
        val sdkVersion: Int,
        val androidVersion: String
    )

    companion object {
        fun toCommand(loginUserRequest: LoginUserRequest): LoginUserCommand {
            val loginUserCommand = LoginUserCommand(
                loginUserRequest.uid,
                loginUserRequest.username,
                loginUserRequest.email,
                loginUserRequest.deviceInfo?.let {
                    LoginUserCommand.DeviceInfoDTO(
                        deviceId = it.deviceId,
                        brand = it.brand,
                        model = it.model,
                        sdkVersion = it.sdkVersion,
                        androidVersion = it.androidVersion
                    )
                }
            )

            return loginUserCommand
        }
    }
}


