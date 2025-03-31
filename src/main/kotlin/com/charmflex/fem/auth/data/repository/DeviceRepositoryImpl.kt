package com.charmflex.fem.auth.data.repository

import com.charmflex.fem.auth.domain.model.Device
import com.charmflex.fem.auth.domain.repository.DeviceRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
internal class DeviceRepositoryImpl(
    jdbcTemplate: JdbcTemplate
): DeviceRepository {
    private val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

    override fun save(device: Device): Device {
        val sql = """
            INSERT INTO devices (device_id, brand, model, sdk_version, android_version)
            VALUES (:deviceId, :brand, :model, :sdkVersion, :androidVersion)
            ON CONFLICT (device_id) DO UPDATE
            SET brand = EXCLUDED.brand, 
                model = EXCLUDED.model, 
                sdk_version = EXCLUDED.sdk_version, 
                android_version = EXCLUDED.android_version
            RETURNING *
        """.trimIndent()

        val params = hashMapOf(
            "deviceId" to device.deviceId,
            "brand" to device.brand,
            "model" to device.model,
            "sdkVersion" to device.sdkVersion,
            "androidVersion" to device.androidVersion
        )

        val result = RowMapper<Device> { rs: ResultSet, rowNum: Int ->
            Device(
                deviceId = rs.getString("device_id"),
                brand = rs.getString("brand"),
                model = rs.getString("model"),
                sdkVersion = rs.getInt("sdk_version"),
                androidVersion = rs.getString("android_version")
            )
        }

        return namedParameterJdbcTemplate.queryForObject(sql, params, result)!!
    }
}