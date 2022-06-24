package com.jeeplus.database.datamodel.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.database.datamodel.domain.DataParam;
import com.jeeplus.database.datamodel.service.dto.DataParamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DataParamWrapper  extends EntityWrapper <DataParamDTO, DataParam> {

    DataParamWrapper INSTANCE = Mappers.getMapper( DataParamWrapper.class);

    @Mappings({
            @Mapping(source = "dataSetDTO.id", target = "dataSetId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    DataParam toEntity(DataParamDTO dto);


    @Mappings({
            @Mapping(source = "dataSetId", target = "dataSetDTO.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    DataParamDTO toDTO(DataParam entity);

}
