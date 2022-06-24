package com.jeeplus.calendar.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.calendar.domain.MyCalendar;
import com.jeeplus.calendar.service.dto.MyCalendarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface MyCalendarWrapper extends EntityWrapper <MyCalendarDTO, MyCalendar> {

    MyCalendarWrapper INSTANCE = Mappers.getMapper( MyCalendarWrapper.class);

    @Mappings({
            @Mapping(source = "userDTO.id", target = "userId")})
    MyCalendar toEntity(MyCalendarDTO dto);


    @Mappings({
            @Mapping (source = "userId", target = "userDTO.id")})
    MyCalendarDTO toDTO(MyCalendar entity);

}
