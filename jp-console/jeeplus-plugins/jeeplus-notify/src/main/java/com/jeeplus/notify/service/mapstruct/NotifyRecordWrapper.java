package com.jeeplus.notify.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.notify.domain.NotifyRecord;
import com.jeeplus.notify.service.dto.NotifyRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface NotifyRecordWrapper extends EntityWrapper <NotifyRecordDTO, NotifyRecord> {

    NotifyRecordWrapper INSTANCE = Mappers.getMapper( NotifyRecordWrapper.class);

    @Mappings({
            @Mapping(source = "notifyDTO.id", target = "notifyId"),
            @Mapping(source = "userDTO.id", target = "userId"),
            @Mapping (source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    NotifyRecord toEntity(NotifyRecordDTO dto);


    @Mappings({
            @Mapping (source = "notifyId", target = "notifyDTO.id"),
            @Mapping (source = "userId", target = "userDTO.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    NotifyRecordDTO toDTO(NotifyRecord entity);

}
