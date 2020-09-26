package com.regions.dictionary.controllers

import com.regions.dictionary.domain.Region
import com.regions.dictionary.mappers.RegionMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DictionaryController {
    @Autowired
    lateinit var regionMapper: RegionMapper

    @GetMapping("/")
    fun getDictionaryOfRegions(model: Model) : MutableList<Region> {
        val list = ArrayList<Region>()
        list.addAll(regionMapper.getRegions())
        model.addAttribute("regions", list)
        return list
    }
}