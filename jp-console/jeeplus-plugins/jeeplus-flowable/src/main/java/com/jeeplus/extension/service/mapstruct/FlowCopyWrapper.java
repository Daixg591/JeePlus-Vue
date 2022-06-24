package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.service.dto.FlowCopyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FlowCopyWrapper extends EntityWrapper <FlowCopyDTO, FlowCopy> {

    FlowCopyWrapper INSTANCE = Mappers.getMapper( FlowCopyWrapper.class);

}
