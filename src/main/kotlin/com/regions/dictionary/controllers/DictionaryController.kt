package com.regions.dictionary.controllers

import com.regions.dictionary.domain.Region
import com.regions.dictionary.mappers.RegionMapper
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
    fun getDictionaryOfRegions() : MutableList<Region> = regionMapper.selectRegions()


    @GetMapping("{id}")
    @Cacheable("regions", key = "#id")
    fun getRegionFromDictionary(@PathVariable("id") id: Long) = regionMapper.selectRegionById(id)

    @PostMapping
    @Cacheable("regions", key = "#region.id" )
    fun addNewRegionToDictionary(@RequestBody region: Region){
        regionMapper.insertRegionToDictionary(region)
    }

    @PutMapping("{id}")
    @CachePut("regions", key = "#id")
    fun updateRegionInDictionary(
            @PathVariable("id") id: Long,
            @RequestBody regionFromUser: Region
    ){
        val regionFromDatabase = regionMapper.selectRegionById(id)
        BeanUtils.copyProperties(regionFromUser, regionFromDatabase, "id")
        regionMapper.updateRegionInDictionary(regionFromDatabase)
    }

    @DeleteMapping("{id}")
    @CacheEvict("regions", key = "#id")
    fun deleteRegionFromDictionaryById(@PathVariable("id") id: Long){
        regionMapper.deleteRegionFromDictionaryById(id)
    }
}