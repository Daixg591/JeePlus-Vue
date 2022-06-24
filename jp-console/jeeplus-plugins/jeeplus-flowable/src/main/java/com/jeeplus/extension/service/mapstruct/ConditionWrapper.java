package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.Condition;
import com.jeeplus.extension.service.dto.ConditionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ConditionWrapper extends EntityWrapper <ConditionDTO, Condition> {

    ConditionWrapper INSTANCE = Mappers.getMapper( ConditionWrapper.class);

}
