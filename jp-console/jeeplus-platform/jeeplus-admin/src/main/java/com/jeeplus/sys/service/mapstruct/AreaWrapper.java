package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.sys.service.dto.AreaDTO;
import com.jeeplus.sys.domain.Area;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface AreaWrapper extends TreeWrapper<AreaDTO, Area> {

    AreaWrapper INSTANCE = Mappers.getMapper(AreaWrapper.class);
}
