package com.jeeplus.mail.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.service.dto.MailComposeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface MailComposeWrapper extends EntityWrapper <MailComposeDTO, MailCompose> {

    MailComposeWrapper INSTANCE = Mappers.getMapper( MailComposeWrapper.class);

    @Mappings({
            @Mapping ( source = "mailDTO.id", target = "mailId"),
            @Mapping ( source = "sender.id", target = "senderId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    MailCompose toEntity(MailComposeDTO dto);


    @Mappings({
            @Mapping ( source = "mailId", target = "mailDTO.id"),
            @Mapping ( source = "senderId", target = "sender.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    MailComposeDTO toDTO(MailCompose entity);
}
