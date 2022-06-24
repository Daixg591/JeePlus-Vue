/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.manytomany.service.dto.StudentDTO;
import com.jeeplus.test.manytomany.domain.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  StudentWrapper
 * @author 刘高峰
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface StudentWrapper extends EntityWrapper<StudentDTO, Student> {

    StudentWrapper INSTANCE = Mappers.getMapper(StudentWrapper.class);
}

