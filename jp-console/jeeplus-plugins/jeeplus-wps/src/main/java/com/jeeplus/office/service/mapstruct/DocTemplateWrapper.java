package com.jeeplus.office.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.office.domain.DocTemplate;
import com.jeeplus.office.service.dto.DocTemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DocTemplateWrapper extends EntityWrapper <DocTemplateDTO, DocTemplate> {

    DocTemplateWrapper INSTANCE = Mappers.getMapper( DocTemplateWrapper.class);


    @Mappings({
            @Mapping(source = "docCategoryDTO.id", target = "categoryId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    DocTemplate toEntity(DocTemplateDTO dto);


    @Mappings({
            @Mapping (source = "categoryId", target = "docCategoryDTO.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    DocTemplateDTO toDTO(DocTemplate entity);
}
