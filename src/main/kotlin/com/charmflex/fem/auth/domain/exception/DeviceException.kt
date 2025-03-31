package com.charmflex.fem.auth.domain.exception

import org.springframework.aop.ThrowsAdvice
import kotlin.jvm.Throws

internal sealed class DeviceException : Exception() {
    data class DeviceInvalidParamsException(
        val param: String,
        override val message: String
    ) : DeviceException()
}
