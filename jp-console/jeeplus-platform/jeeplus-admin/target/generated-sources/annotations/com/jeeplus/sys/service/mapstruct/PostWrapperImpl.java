package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.Post;
import com.jeeplus.sys.service.dto.PostDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:52:36+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class PostWrapperImpl implements PostWrapper {

    @Override
    public PostDTO toDTO(Post arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setCreateBy( postToUserDTO( arg0 ) );
        postDTO.setUpdateBy( postToUserDTO1( arg0 ) );
        postDTO.setCreateDate( arg0.getCreateDate() );
        postDTO.setDelFlag( arg0.getDelFlag() );
        postDTO.setId( arg0.getId() );
        postDTO.setUpdateDate( arg0.getUpdateDate() );
        postDTO.setCode( arg0.getCode() );
        postDTO.setName( arg0.getName() );
        postDTO.setRemarks( arg0.getRemarks() );
        postDTO.setSort( arg0.getSort() );
        postDTO.setStatus( arg0.getStatus() );
        postDTO.setType( arg0.getType() );

        return postDTO;
    }

    @Override
    public List<PostDTO> toDTO(List<Post> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( entityList.size() );
        for ( Post post : entityList ) {
            list.add( toDTO( post ) );
        }

        return list;
    }

    @Override
    public Page<PostDTO> toDTO(Page<Post> page) {
        if ( page == null ) {
            return null;
        }

        Page<PostDTO> page1 = new Page<PostDTO>();

        page1.setPages( page.getPages() );
        page1.setCountId( page.getCountId() );
        page1.setCurrent( page.getCurrent() );
        page1.setHitCount( page.isHitCount() );
        page1.setMaxLimit( page.getMaxLimit() );
        page1.setOptimizeCountSql( page.isOptimizeCountSql() );
        List<OrderItem> list = page.getOrders();
        if ( list != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list ) );
        }
        page1.setRecords( toDTO( page.getRecords() ) );
        page1.setSearchCount( page.isSearchCount() );
        page1.setSize( page.getSize() );
        page1.setTotal( page.getTotal() );

        return page1;
    }

    @Override
    public Post toEntity(PostDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Post post = new Post();

        post.setCreateBy( arg0CreateById( arg0 ) );
        post.setUpdateBy( arg0UpdateById( arg0 ) );
        post.setCreateDate( arg0.getCreateDate() );
        post.setDelFlag( arg0.getDelFlag() );
        post.setId( arg0.getId() );
        post.setUpdateDate( arg0.getUpdateDate() );
        post.setCode( arg0.getCode() );
        post.setName( arg0.getName() );
        post.setRemarks( arg0.getRemarks() );
        post.setSort( arg0.getSort() );
        post.setStatus( arg0.getStatus() );
        post.setType( arg0.getType() );

        return post;
    }

    @Override
    public List<Post> toEntity(List<PostDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Post> list = new ArrayList<Post>( dtoList.size() );
        for ( PostDTO postDTO : dtoList ) {
            list.add( toEntity( postDTO ) );
        }

        return list;
    }

    @Override
    public Page<Post> toEntity(Page<PostDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Post> page1 = new Page<Post>();

        page1.setPages( page.getPages() );
        page1.setCountId( page.getCountId() );
        page1.setCurrent( page.getCurrent() );
        page1.setHitCount( page.isHitCount() );
        page1.setMaxLimit( page.getMaxLimit() );
        page1.setOptimizeCountSql( page.isOptimizeCountSql() );
        List<OrderItem> list = page.getOrders();
        if ( list != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list ) );
        }
        page1.setRecords( toEntity( page.getRecords() ) );
        page1.setSearchCount( page.isSearchCount() );
        page1.setSize( page.getSize() );
        page1.setTotal( page.getTotal() );

        return page1;
    }

    protected UserDTO postToUserDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( post.getCreateBy() );

        return userDTO;
    }

    protected UserDTO postToUserDTO1(Post post) {
        if ( post == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( post.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }
        UserDTO createBy = postDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }
        UserDTO updateBy = postDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
