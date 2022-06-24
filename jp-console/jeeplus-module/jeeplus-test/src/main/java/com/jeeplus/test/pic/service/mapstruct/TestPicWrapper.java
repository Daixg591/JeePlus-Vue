/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.pic.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.pic.service.dto.TestPicDTO;
import com.jeeplus.test.pic.domain.TestPic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestPicWrapper
 * @author lgf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestPicWrapper extends EntityWrapper<TestPicDTO, TestPic> {

    TestPicWrapper INSTANCE = Mappers.getMapper(TestPicWrapper.class);
}

