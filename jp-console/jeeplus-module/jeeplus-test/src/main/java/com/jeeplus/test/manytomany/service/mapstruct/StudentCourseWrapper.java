/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.manytomany.service.dto.StudentCourseDTO;
import com.jeeplus.test.manytomany.domain.StudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  StudentCourseWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface StudentCourseWrapper extends EntityWrapper<StudentCourseDTO, StudentCourse> {

    StudentCourseWrapper INSTANCE = Mappers.getMapper(StudentCourseWrapper.class);
     @Mappings({
            @Mapping(source = "student.id", target = "studentId"),
            @Mapping(source = "course.id", target = "courseId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    StudentCourse toEntity(StudentCourseDTO dto);


    @Mappings({
            @Mapping(source = "studentId", target = "student.id"),
            @Mapping(source = "courseId", target = "course.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    StudentCourseDTO toDTO(StudentCourse entity);
}

