package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FlowAssignee;
import com.jeeplus.extension.service.dto.FlowAssigneeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FlowAssigneeWrapper extends EntityWrapper <FlowAssigneeDTO, FlowAssignee> {

    FlowAssigneeWrapper INSTANCE = Mappers.getMapper( FlowAssigneeWrapper.class);

    @Mappings({
            @Mapping (source = "taskDef.id", target = "taskDefId"),
            @Mapping (source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    FlowAssignee toEntity(FlowAssigneeDTO dto);


    @Mappings({
            @Mapping (source = "taskDefId", target = "taskDef.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    FlowAssigneeDTO toDTO(FlowAssignee entity);

}
