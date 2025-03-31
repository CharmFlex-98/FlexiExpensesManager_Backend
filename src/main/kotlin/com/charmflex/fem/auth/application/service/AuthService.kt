package com.charmflex.fem.auth.application.service

import com.charmflex.fem.auth.domain.repository.AuthRepository
import com.charmflex.fem.auth.application.model.LoginUserCommand
import com.charmflex.fem.auth.application.model.LoginUserResult
import com.charmflex.fem.auth.domain.model.Device
import com.charmflex.fem.auth.domain.model.User
import com.charmflex.fem.auth.domain.repository.DeviceRepository
import com.charmflex.fem.auth.domain.repository.UserDeviceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
internal class AuthService(
    private val authRepository: AuthRepository,
    private val deviceRepository: DeviceRepository,
    private val userDeviceRepository: UserDeviceRepository
) {

    @Transactional
    fun loginUser(loginUserCommand: LoginUserCommand): LoginUserResult {
        val user = User(
            uid = loginUserCommand.uid,
            username = loginUserCommand.username,
            email = loginUserCommand.email,
            lastLogin = Instant.now(),
            createdAt = Instant.now(),
            modifiedAt = Instant.now(),
        )
        val res = authRepository.save(user)
        // If there is device info, then update it in the database
        loginUserCommand.deviceInfo?.let {
            val device = Device(
                deviceId = it.deviceId,
                brand = it.brand,
                model = it.model,
                sdkVersion = it.sdkVersion,
                androidVersion = it.androidVersion
            )
            val deviceOutput = deviceRepository.save(device)
            userDeviceRepository.save(res.uid, deviceOutput.deviceId)
        }

        // Link user and device
        return LoginUserResult(res.uid, res.username, res.createdAt, res.isNewUser)
    }
}