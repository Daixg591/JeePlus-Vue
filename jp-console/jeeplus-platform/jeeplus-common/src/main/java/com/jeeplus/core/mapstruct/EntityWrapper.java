package com.jeeplus.core.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

public interface EntityWrapper<D, E> {

    @Mappings({
            @Mapping (source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    E toEntity(D dto);


    @Mappings({
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    D toDTO(E entity);

    List<E> toEntity(List <D> dtoList);


    List<D> toDTO(List <E> entityList);


    Page <E> toEntity(Page <D> page);

    Page <D> toDTO(Page <E> page);

}
