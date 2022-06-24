package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.RoleDTO;
import com.jeeplus.sys.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface RoleWrapper extends EntityWrapper <RoleDTO, Role> {

    RoleWrapper INSTANCE = Mappers.getMapper(RoleWrapper.class);

}
