/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.shop.service.dto.GoodsDTO;
import com.jeeplus.test.shop.domain.Goods;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  GoodsWrapper
 * @author liugf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface GoodsWrapper extends EntityWrapper<GoodsDTO, Goods> {

    GoodsWrapper INSTANCE = Mappers.getMapper(GoodsWrapper.class);
     @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Goods toEntity(GoodsDTO dto);


    @Mappings({
            @Mapping(source = "categoryId", target = "category.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    GoodsDTO toDTO(Goods entity);
}

