package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.Listener;
import com.jeeplus.extension.service.dto.ListenerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ListenerWrapper extends EntityWrapper <ListenerDTO, Listener> {

    ListenerWrapper INSTANCE = Mappers.getMapper( ListenerWrapper.class);

}
