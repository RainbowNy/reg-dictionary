package com.regions.dictionary

import com.regions.dictionary.configurations.PersistenceConfiguration
import com.regions.dictionary.mappers.RegionMapper
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = [PersistenceConfiguration::class])
class RegionMapperIntegrationTest {

    @Autowired
    lateinit var regionMapper: RegionMapper
}
