/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.mapstruct;


import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.test.shop.service.dto.CategoryDTO;
import com.jeeplus.test.shop.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CategoryWrapper
 * @author liugf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CategoryWrapper extends TreeWrapper<CategoryDTO, Category> {

    CategoryWrapper INSTANCE = Mappers.getMapper(CategoryWrapper.class);
     @Mappings({
            @Mapping(source = "parent.id", target = "parentId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Category toEntity(CategoryDTO dto);


    @Mappings({
            @Mapping(source = "parentId", target = "parent.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    CategoryDTO toDTO(Category entity);
}

