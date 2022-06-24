/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.note.service.dto.TestNoteDTO;
import com.jeeplus.test.note.domain.TestNote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestNoteWrapper
 * @author liugf
 * @version 2021-10-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestNoteWrapper extends EntityWrapper<TestNoteDTO, TestNote> {

    TestNoteWrapper INSTANCE = Mappers.getMapper(TestNoteWrapper.class);
}

