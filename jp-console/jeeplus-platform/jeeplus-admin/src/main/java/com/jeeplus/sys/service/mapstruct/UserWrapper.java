package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface UserWrapper extends EntityWrapper <UserDTO, User> {


    UserWrapper INSTANCE = Mappers.getMapper(UserWrapper.class);

    @Mappings({
            @Mapping(source = "companyDTO.id", target = "companyId"),
            @Mapping(source = "officeDTO.id", target = "officeId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    User toEntity(UserDTO dto);


    @Mappings({
            @Mapping (source = "companyId", target = "companyDTO.id"),
            @Mapping (source = "officeId", target = "officeDTO.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    UserDTO toDTO(User entity);


}
