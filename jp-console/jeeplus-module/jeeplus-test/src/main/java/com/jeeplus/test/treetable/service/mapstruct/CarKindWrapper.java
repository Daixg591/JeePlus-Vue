/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.mapstruct;


import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.test.treetable.service.dto.CarKindDTO;
import com.jeeplus.test.treetable.domain.CarKind;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CarKindWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CarKindWrapper extends TreeWrapper<CarKindDTO, CarKind> {

    CarKindWrapper INSTANCE = Mappers.getMapper(CarKindWrapper.class);
}

