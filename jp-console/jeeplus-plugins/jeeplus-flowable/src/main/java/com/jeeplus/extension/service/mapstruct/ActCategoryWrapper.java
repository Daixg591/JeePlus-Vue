package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.extension.domain.ActCategory;
import com.jeeplus.extension.service.dto.ActCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ActCategoryWrapper extends TreeWrapper <ActCategoryDTO, ActCategory> {

    ActCategoryWrapper INSTANCE = Mappers.getMapper( ActCategoryWrapper.class);


}
