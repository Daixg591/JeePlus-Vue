/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.grid.service.dto.TestContinentDTO;
import com.jeeplus.test.grid.domain.TestContinent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestContinentWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestContinentWrapper extends EntityWrapper<TestContinentDTO, TestContinent> {

    TestContinentWrapper INSTANCE = Mappers.getMapper(TestContinentWrapper.class);
}

