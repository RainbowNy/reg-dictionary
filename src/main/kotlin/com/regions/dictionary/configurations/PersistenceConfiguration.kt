package com.regions.dictionary.configurations

import com.regions.dictionary.mappers.RegionMapper
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
@MapperScan("com.regions.dictionary")
class PersistenceConfiguration {

    @Bean
    fun getDataSource() : DataSource {
        return EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql_scripts/schema.sql")
                .addScript("sql_scripts/data.sql")
                .build()
    }

    @Bean
    fun getSqlSessionFactory() : SqlSessionFactory? {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(getDataSource())
        return factoryBean.`object`;
    }

    @Bean
    fun getRegionMapper() : RegionMapper {
        val sqlSessionTemplate = SqlSessionTemplate(getSqlSessionFactory())
        return sqlSessionTemplate.getMapper(RegionMapper::class.java)
    }
}