package com.regions.dictionary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DictionaryApplication

fun main(args: Array<String>) {
	runApplication<DictionaryApplication>(*args)
}
