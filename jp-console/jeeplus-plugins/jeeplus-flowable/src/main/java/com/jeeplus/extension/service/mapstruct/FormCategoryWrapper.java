package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.extension.domain.FormCategory;
import com.jeeplus.extension.service.dto.FormCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FormCategoryWrapper extends TreeWrapper <FormCategoryDTO, FormCategory> {

    FormCategoryWrapper INSTANCE = Mappers.getMapper( FormCategoryWrapper.class);

}
