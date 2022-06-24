package com.jeeplus.form.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.form.domain.Form;
import com.jeeplus.form.service.dto.FormDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FormWrapper extends EntityWrapper <FormDTO, Form> {

    FormWrapper INSTANCE = Mappers.getMapper( FormWrapper.class);

    @Mappings({
            @Mapping (source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy"),
            @Mapping(source = "dataSource.id", target = "dataSourceId")})
    Form toEntity(FormDTO dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id"),
            @Mapping (source = "dataSourceId", target = "dataSource.id")})
    FormDTO toDTO(Form entity);

}
