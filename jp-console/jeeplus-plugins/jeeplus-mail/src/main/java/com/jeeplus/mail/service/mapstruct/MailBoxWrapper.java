package com.jeeplus.mail.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.mail.service.dto.MailBoxDTO;
import com.jeeplus.mail.domain.MailBox;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface MailBoxWrapper extends EntityWrapper <MailBoxDTO, MailBox> {

    MailBoxWrapper INSTANCE = Mappers.getMapper( MailBoxWrapper.class);

    @Mappings({
            @Mapping ( source = "mailDTO.id", target = "mailId"),
            @Mapping ( source = "sender.id", target = "senderId"),
            @Mapping ( source = "receiver.id", target = "receiverId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    MailBox toEntity(MailBoxDTO dto);


    @Mappings({
            @Mapping ( source = "mailId", target = "mailDTO.id"),
            @Mapping ( source = "senderId", target = "sender.id"),
            @Mapping ( source = "receiverId", target = "receiver.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    MailBoxDTO toDTO(MailBox entity);
}
