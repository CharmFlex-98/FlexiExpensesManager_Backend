package com.charmflex.fem.auth.domain.model

import java.time.Instant

data class User(
    val uid: String,
    val username: String?,
    val email: String?,
    val lastLogin: Instant,
    val createdAt: Instant,
    val modifiedAt: Instant,
) {
    val isNewUser: Boolean
        get() {
            return createdAt == lastLogin
        }
}