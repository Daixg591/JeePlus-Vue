package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.DictTypeDTO;
import com.jeeplus.sys.domain.DictType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DictTypeWrapper extends EntityWrapper <DictTypeDTO, DictType> {

    DictTypeWrapper INSTANCE = Mappers.getMapper(DictTypeWrapper.class);
}
