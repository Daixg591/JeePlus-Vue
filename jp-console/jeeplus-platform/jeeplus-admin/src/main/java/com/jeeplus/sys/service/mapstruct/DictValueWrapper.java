package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.DictValueDTO;
import com.jeeplus.sys.domain.DictValue;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DictValueWrapper extends EntityWrapper <DictValueDTO, DictValue> {

    DictValueWrapper INSTANCE = Mappers.getMapper(DictValueWrapper.class);
}
