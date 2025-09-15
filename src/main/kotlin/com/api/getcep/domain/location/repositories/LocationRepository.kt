package com.api.getcep.domain.location.repositories

import com.api.getcep.domain.location.entities.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<LocationEntity, Long> {
    fun findByCep(cep: String): LocationEntity?
}