package com.charmflex.fem.auth.application.model

import java.time.Instant

data class LoginUserResult(
    val uid: String,
    val name: String?,
    val createdAt: Instant,
    val isNewUser: Boolean
)