/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.service.mapstruct;


import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.test.tree.service.dto.TestTreeDTO;
import com.jeeplus.test.tree.domain.TestTree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestTreeWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestTreeWrapper extends TreeWrapper<TestTreeDTO, TestTree> {

    TestTreeWrapper INSTANCE = Mappers.getMapper(TestTreeWrapper.class);
     @Mappings({
            @Mapping(source = "parent.id", target = "parentId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestTree toEntity(TestTreeDTO dto);


    @Mappings({
            @Mapping(source = "parentId", target = "parent.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestTreeDTO toDTO(TestTree entity);
}

