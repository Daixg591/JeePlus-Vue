package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.Button;
import com.jeeplus.extension.service.dto.ButtonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ButtonWrapper extends EntityWrapper <ButtonDTO, Button> {

    ButtonWrapper INSTANCE = Mappers.getMapper( ButtonWrapper.class);

}
