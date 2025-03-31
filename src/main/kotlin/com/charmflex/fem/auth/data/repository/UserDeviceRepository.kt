package com.charmflex.fem.auth.data.repository

import com.charmflex.fem.auth.domain.repository.UserDeviceRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.Instant

@Repository
internal class UserDeviceRepository(
    jdbcTemplate: JdbcTemplate
) : UserDeviceRepository {
    private val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)
    override fun save(userId: String, deviceId: String) {
        val sql = """
            INSERT INTO user_devices (user_id, device_id, last_used)
            VALUES (:userId, :deviceId, :lastUsed)
            ON CONFLICT(user_id, device_id) DO UPDATE
            SET last_used = EXCLUDED.last_used
        """.trimIndent()

        val params = hashMapOf(
            "userId" to userId,
            "deviceId" to deviceId,
            "lastUsed" to Timestamp.from(Instant.now())
        )

        namedParameterJdbcTemplate.update(sql, params)
    }
}