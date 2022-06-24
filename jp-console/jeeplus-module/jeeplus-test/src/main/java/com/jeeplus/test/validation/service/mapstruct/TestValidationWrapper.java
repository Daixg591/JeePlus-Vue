/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.validation.service.dto.TestValidationDTO;
import com.jeeplus.test.validation.domain.TestValidation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestValidationWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestValidationWrapper extends EntityWrapper<TestValidationDTO, TestValidation> {

    TestValidationWrapper INSTANCE = Mappers.getMapper(TestValidationWrapper.class);
}

