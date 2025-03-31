package com.charmflex.fem.auth.data.repository

import com.charmflex.fem.auth.domain.model.User
import com.charmflex.fem.auth.domain.repository.AuthRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.Instant

@Repository
internal class AuthRepositoryImpl(
    jdbcTemplate: JdbcTemplate
) : AuthRepository {
    private val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

    override fun save(user: User): User {
        val sql = """
            INSERT INTO users (uid, username, email, last_login, created_at, modified_at)
            VALUES (:uid, :username, :email, :lastLogin, :createdAt, :modifiedAt)
            ON CONFLICT (uid) DO UPDATE
            SET last_login = EXCLUDED.last_login,
                modified_at = EXCLUDED.modified_at
            RETURNING *
        """.trimIndent()
        val currentTime = Timestamp.from(Instant.now())
        val params = mapOf(
            "uid" to user.uid,
            "username" to user.username,
            "email" to user.email,
            "lastLogin" to currentTime,
            "createdAt" to currentTime,
            "modifiedAt" to currentTime
        )
        val rowMapper: RowMapper<User> = RowMapper { result, _ ->
            val createdAt = result.getTimestamp("created_at")
            val modifiedAt = result.getTimestamp("modified_at")
            val lastLogin = result.getTimestamp("last_login")
            User(
                uid = result.getString("uid"),
                username = result.getString("username"),
                email = result.getString("email"),
                lastLogin = lastLogin.toInstant(),
                createdAt = createdAt.toInstant(),
                modifiedAt = modifiedAt.toInstant(),
            )
        }
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper)!!
    }
}