package com.regions.dictionary.controllers

import com.regions.dictionary.domain.Region
import com.regions.dictionary.mappers.RegionMapper
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class DictionaryController {
    @Autowired
    lateinit var regionMapper: RegionMapper

    @GetMapping
    fun getDictionaryOfRegions() : MutableList<Region> {
        val list = ArrayList<Region>()
        list.addAll(regionMapper.selectRegions())
        return list
    }

    @PostMapping
    fun addNewRegionToDictionary(@RequestBody region: Region){
        regionMapper.insertRegionToDictionary(region)
    }

    @PutMapping("{id}")
    fun updateRegionInDictionary(
            @PathVariable("id") regionFromDatabase: Region,
            @RequestBody regionFromUser: Region
    ){
        BeanUtils.copyProperties(regionFromUser, regionFromDatabase, "id")
        regionMapper.updateRegionInDictionary(regionFromDatabase)
    }

    @DeleteMapping("{id}")
    fun deleteRegionFromDictionaryById(@PathVariable("id") selectedRegion: Region){
        regionMapper.deleteRegionFromDictionaryById(selectedRegion)
    }
}