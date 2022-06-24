package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.service.dto.PostDTO;
import com.jeeplus.sys.domain.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface PostWrapper extends EntityWrapper <PostDTO, Post> {

    PostWrapper INSTANCE = Mappers.getMapper(PostWrapper.class);
}
