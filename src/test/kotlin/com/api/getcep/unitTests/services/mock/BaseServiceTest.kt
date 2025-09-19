package com.api.getcep.unitTests.services.mock

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.integrations.client.GetLocationClient
import com.api.getcep.services.GetLocationByIdService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach

abstract class BaseServiceTest {

    @MockK
    protected lateinit var locationRepository: LocationRepository

    @MockK
    protected lateinit var getLocationByIdService: GetLocationByIdService

    @MockK
    protected lateinit var getLocationClient: GetLocationClient

    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }
}