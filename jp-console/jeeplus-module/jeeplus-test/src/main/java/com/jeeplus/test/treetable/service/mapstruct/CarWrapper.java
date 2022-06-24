/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.treetable.service.dto.CarDTO;
import com.jeeplus.test.treetable.domain.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CarWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CarWrapper extends EntityWrapper<CarDTO, Car> {

    CarWrapper INSTANCE = Mappers.getMapper(CarWrapper.class);
     @Mappings({
            @Mapping(source = "kind.id", target = "kindId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Car toEntity(CarDTO dto);


    @Mappings({
            @Mapping(source = "kindId", target = "kind.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    CarDTO toDTO(Car entity);
}

