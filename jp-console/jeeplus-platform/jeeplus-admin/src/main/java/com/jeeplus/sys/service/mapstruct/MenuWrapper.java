package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface MenuWrapper extends TreeWrapper<MenuDTO, Menu> {

    MenuWrapper INSTANCE = Mappers.getMapper(MenuWrapper.class);
}
