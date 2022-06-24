package com.jeeplus.database.datamodel.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.database.datamodel.domain.DataSet;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DataSetWrapper extends EntityWrapper <DataSetDTO, DataSet> {

    DataSetWrapper INSTANCE = Mappers.getMapper( DataSetWrapper.class);

    @Mappings({
            @Mapping(source = "dataSource.id", target = "dataSourceId")})
    DataSet toEntity(DataSetDTO dto);


    @Mappings({
            @Mapping(source = "dataSourceId", target = "dataSource.id")})
    DataSetDTO toDTO(DataSet entity);

}
