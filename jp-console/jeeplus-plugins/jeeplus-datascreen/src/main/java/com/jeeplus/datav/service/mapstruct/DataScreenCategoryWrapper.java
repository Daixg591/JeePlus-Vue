package com.jeeplus.datav.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.datav.domain.DataScreenCategory;
import com.jeeplus.datav.service.dto.DataScreenCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface DataScreenCategoryWrapper extends TreeWrapper <DataScreenCategoryDTO, DataScreenCategory> {

    DataScreenCategoryWrapper INSTANCE = Mappers.getMapper( DataScreenCategoryWrapper.class);


}
