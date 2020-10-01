package com.regions.dictionary.controllers

import com.regions.dictionary.domain.Region
import com.regions.dictionary.mappers.RegionMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/region")
class DictionaryController {
    @Autowired
    lateinit var regionMapper: RegionMapper

    @GetMapping
    @Cacheable(value = ["regions"])
    fun getDictionaryOfRegions() : MutableList<Region> = regionMapper.selectRegions()


    @GetMapping("{id}")
    @Cacheable(value = ["regions"])
    fun getRegionFromDictionary(@PathVariable("id") id: Long) = regionMapper.selectRegionById(id)

    @PostMapping
    @CacheEvict(value = ["regions"], allEntries = true)
    fun addNewRegionToDictionary(@RequestBody region: Region): Region? {
        regionMapper.insertRegionToDictionary(region)
        return region
    }

    @PutMapping("{id}")
    @CacheEvict(value = ["regions"], allEntries = true)
    fun updateRegionInDictionary(
            @PathVariable("id") id: Long,
            @RequestBody regionFromUser: Region
    ): Region {
        val regionFromDatabase = regionMapper.selectRegionById(id)
        BeanUtils.copyProperties(regionFromUser, regionFromDatabase, "id")
        regionMapper.updateRegionInDictionary(regionFromDatabase)
        return regionFromDatabase
    }

    @DeleteMapping("{id}")
    @CacheEvict("regions", allEntries = true)
    fun deleteRegionFromDictionaryById(@PathVariable("id") id: Long){
        regionMapper.deleteRegionFromDictionaryById(id)
    }
}