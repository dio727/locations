package com.api.getcep.integrationTest.mock

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import com.api.getcep.Application
import com.api.getcep.domain.location.repositories.LocationRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc


@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
class BaseIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var locationRepository: LocationRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper
}