package com.jeeplus.datav.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.datav.domain.DataScreen;
import com.jeeplus.datav.service.dto.DataScreenDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface DataScreenWrapper extends EntityWrapper <DataScreenDTO, DataScreen> {

    DataScreenWrapper INSTANCE = Mappers.getMapper( DataScreenWrapper.class);

    @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    DataScreen toEntity(DataScreenDTO dto);


    @Mappings({
            @Mapping (source = "categoryId", target = "category.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    DataScreenDTO toDTO(DataScreen entity);

}
