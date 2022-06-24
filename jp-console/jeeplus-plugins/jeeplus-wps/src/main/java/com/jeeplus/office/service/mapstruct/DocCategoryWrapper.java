package com.jeeplus.office.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.office.domain.DocCategory;
import com.jeeplus.office.service.dto.DocCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DocCategoryWrapper extends TreeWrapper <DocCategoryDTO, DocCategory> {

    DocCategoryWrapper INSTANCE = Mappers.getMapper( DocCategoryWrapper.class);
}
