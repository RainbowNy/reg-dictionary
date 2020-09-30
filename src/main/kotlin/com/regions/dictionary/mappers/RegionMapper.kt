package com.regions.dictionary.mappers

import com.regions.dictionary.domain.Region
import org.apache.ibatis.annotations.*
import org.springframework.cache.annotation.Cacheable

@Mapper
interface RegionMapper {
    @Results(
        Result(property = "name", column = "name"),
        Result(property = "shortName", column = "shortName"),
        Result(property = "id", column = "id")
    )
    @Select("SELECT * FROM REGIONS")
    fun selectRegions() : MutableList<Region>

    @Select("SELECT * FROM REGIONS WHERE id = #{id}")
    fun selectRegionById(@Param("id") id: Long) : Region

    @Insert("INSERT INTO REGIONS(name, shortName) VALUE (#{name}, #{shortName})")
    fun insertRegionToDictionary(region: Region)

    @Update("UPDATE REGIONS SET name = #{name}, shortName = #{shortName} WHERE id = #{id}")
    fun updateRegionInDictionary(region: Region)

    @Delete("DELETE FROM REGIONS WHERE id = #{id}")
    fun deleteRegionFromDictionaryById(@Param("id") idRegionToDelete: Long)
}