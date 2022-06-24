package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.domain.DataRule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DataRuleWrapper extends EntityWrapper <DataRuleDTO, DataRule> {

    DataRuleWrapper INSTANCE = Mappers.getMapper(DataRuleWrapper.class);
}
