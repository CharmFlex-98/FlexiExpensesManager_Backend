package com.charmflex.fem.auth.domain.repository

import com.charmflex.fem.auth.domain.model.User

internal interface AuthRepository {
    fun save(user: User): User
}