package com.jeeplus.datav.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.datav.domain.DataMap;
import com.jeeplus.datav.service.dto.DataMapDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface DataMapWrapper extends EntityWrapper <DataMapDTO, DataMap> {

    DataMapWrapper INSTANCE = Mappers.getMapper( DataMapWrapper.class);


}
