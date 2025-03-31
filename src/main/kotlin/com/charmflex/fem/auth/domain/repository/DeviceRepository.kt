package com.charmflex.fem.auth.domain.repository

import com.charmflex.fem.auth.domain.model.Device

internal interface DeviceRepository {
    fun save(device: Device): Device
}