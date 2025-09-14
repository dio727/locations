package com.api.getcep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(basePackages = ["com.api.getcep.client"])
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
