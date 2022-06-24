/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.grid.service.dto.TestCountryDTO;
import com.jeeplus.test.grid.domain.TestCountry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestCountryWrapper
 * @author 刘高峰
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestCountryWrapper extends EntityWrapper<TestCountryDTO, TestCountry> {

    TestCountryWrapper INSTANCE = Mappers.getMapper(TestCountryWrapper.class);
     @Mappings({
            @Mapping(source = "continent.id", target = "continentId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestCountry toEntity(TestCountryDTO dto);


    @Mappings({
            @Mapping(source = "continentId", target = "continent.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestCountryDTO toDTO(TestCountry entity);
}

