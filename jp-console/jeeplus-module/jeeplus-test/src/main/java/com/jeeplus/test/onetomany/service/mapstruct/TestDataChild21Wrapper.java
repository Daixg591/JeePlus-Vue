/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild21DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild21;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataChild21Wrapper
 * @author liugf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataChild21Wrapper extends EntityWrapper<TestDataChild21DTO, TestDataChild21> {

    TestDataChild21Wrapper INSTANCE = Mappers.getMapper(TestDataChild21Wrapper.class);
     @Mappings({
            @Mapping(source = "startArea.id", target = "startAreaId"),
            @Mapping(source = "endArea.id", target = "endAreaId"),
            @Mapping(source = "testDataMainDTO.id", target = "testDataMainId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestDataChild21 toEntity(TestDataChild21DTO dto);


    @Mappings({
            @Mapping(source = "startAreaId", target = "startArea.id"),
            @Mapping(source = "endAreaId", target = "endArea.id"),
            @Mapping(source = "testDataMainId", target = "testDataMainDTO.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestDataChild21DTO toDTO(TestDataChild21 entity);
}

