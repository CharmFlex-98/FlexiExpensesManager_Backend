package com.charmflex.fem.auth.controller.model

import java.time.Instant

data class UpdateUserInfoResponse(
    val createdAt: Instant,
    val newUser: Boolean
)