/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild22DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild22;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataChild22Wrapper
 * @author liugf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataChild22Wrapper extends EntityWrapper<TestDataChild22DTO, TestDataChild22> {

    TestDataChild22Wrapper INSTANCE = Mappers.getMapper(TestDataChild22Wrapper.class);
     @Mappings({
            @Mapping(source = "startArea.id", target = "startAreaId"),
            @Mapping(source = "endArea.id", target = "endAreaId"),
            @Mapping(source = "testDataMain.id", target = "testDataMainId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestDataChild22 toEntity(TestDataChild22DTO dto);


    @Mappings({
            @Mapping(source = "startAreaId", target = "startArea.id"),
            @Mapping(source = "endAreaId", target = "endArea.id"),
            @Mapping(source = "testDataMainId", target = "testDataMain.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestDataChild22DTO toDTO(TestDataChild22 entity);
}

