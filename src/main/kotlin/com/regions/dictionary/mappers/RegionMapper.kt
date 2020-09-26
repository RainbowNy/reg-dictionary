package com.regions.dictionary.mappers

import com.regions.dictionary.domain.Region
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface RegionMapper {
    @Select("SELECT * FROM REGIONS WHERE id = #{id}")
    fun getRegionById(@Param("id") id: Long) : Region

    @Select("SELECT * FROM REGIONS")
    fun getRegions() : MutableList<Region>
}