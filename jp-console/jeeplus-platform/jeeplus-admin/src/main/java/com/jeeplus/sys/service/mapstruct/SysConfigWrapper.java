package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.SysConfigDTO;
import com.jeeplus.sys.domain.SysConfig;
import com.jeeplus.sys.domain.vo.SysConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface SysConfigWrapper extends EntityWrapper <SysConfigDTO, SysConfig> {

    SysConfigWrapper INSTANCE = Mappers.getMapper(SysConfigWrapper.class);

    SysConfigVo toVo(SysConfig sysConfig);

}
