/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild23DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild23;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataChild23Wrapper
 * @author liugf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataChild23Wrapper extends EntityWrapper<TestDataChild23DTO, TestDataChild23> {

    TestDataChild23Wrapper INSTANCE = Mappers.getMapper(TestDataChild23Wrapper.class);
     @Mappings({
            @Mapping(source = "startArea.id", target = "startAreaId"),
            @Mapping(source = "endArea.id", target = "endAreaId"),
            @Mapping(source = "testDataMain.id", target = "testDataMainId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestDataChild23 toEntity(TestDataChild23DTO dto);


    @Mappings({
            @Mapping(source = "startAreaId", target = "startArea.id"),
            @Mapping(source = "endAreaId", target = "endArea.id"),
            @Mapping(source = "testDataMainId", target = "testDataMain.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestDataChild23DTO toDTO(TestDataChild23 entity);
}

