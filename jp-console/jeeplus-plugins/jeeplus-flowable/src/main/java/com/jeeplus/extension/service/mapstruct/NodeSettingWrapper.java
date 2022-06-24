package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.NodeSetting;
import com.jeeplus.extension.service.dto.NodeSettingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface NodeSettingWrapper extends EntityWrapper <NodeSettingDTO, NodeSetting> {

    NodeSettingWrapper INSTANCE = Mappers.getMapper( NodeSettingWrapper.class);

}
