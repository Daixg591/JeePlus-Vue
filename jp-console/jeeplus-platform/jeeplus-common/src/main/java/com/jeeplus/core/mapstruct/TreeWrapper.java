package com.jeeplus.core.mapstruct;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

public interface TreeWrapper<D, E>{

    @Mappings({

            @Mapping (source = "parent.id", target = "parentId"),
            @Mapping (source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    E toEntity(D dto);


    @Mappings({
            @Mapping (source = "parentId", target = "parent.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    D toDTO(E entity);

    E copyEntity(E entity);

    D copyDTO(D dto);

    List<E> toEntity(List <D> dtoList);


    List<D> toDTO(List <E> entityList);

}
