package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.dto.FormDefinitionJsonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FormDefinitionJsonWrapper extends EntityWrapper <FormDefinitionJsonDTO, FormDefinitionJson> {

    FormDefinitionJsonWrapper INSTANCE = Mappers.getMapper( FormDefinitionJsonWrapper.class);

}
