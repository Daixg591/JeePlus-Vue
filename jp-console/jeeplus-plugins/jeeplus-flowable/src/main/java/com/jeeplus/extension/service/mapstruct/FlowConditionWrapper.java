package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FlowCondition;
import com.jeeplus.extension.service.dto.FlowConditionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FlowConditionWrapper extends EntityWrapper <FlowConditionDTO, FlowCondition> {

    FlowConditionWrapper INSTANCE = Mappers.getMapper( FlowConditionWrapper.class);

    @Mappings({
            @Mapping(source = "taskDef.id", target = "taskDefId"),
            @Mapping (source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    FlowCondition toEntity(FlowConditionDTO dto);


    @Mappings({
            @Mapping (source = "taskDefId", target = "taskDef.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    FlowConditionDTO toDTO(FlowCondition entity);

}
