package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.TaskDefExtension;
import com.jeeplus.extension.service.dto.TaskDefExtensionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TaskDefExtensionWrapper extends EntityWrapper <TaskDefExtensionDTO, TaskDefExtension> {

    TaskDefExtensionWrapper INSTANCE = Mappers.getMapper( TaskDefExtensionWrapper.class);

}
