package com.api.getcep.domain.location

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<LocationEntity, Long> {
    fun findByCep(cep: String): LocationEntity?
}