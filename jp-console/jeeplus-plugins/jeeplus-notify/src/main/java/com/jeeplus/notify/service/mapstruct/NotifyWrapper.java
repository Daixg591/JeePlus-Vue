package com.jeeplus.notify.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.notify.domain.Notify;
import com.jeeplus.notify.service.dto.NotifyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface NotifyWrapper extends EntityWrapper <NotifyDTO, Notify> {

    NotifyWrapper INSTANCE = Mappers.getMapper( NotifyWrapper.class);

}
