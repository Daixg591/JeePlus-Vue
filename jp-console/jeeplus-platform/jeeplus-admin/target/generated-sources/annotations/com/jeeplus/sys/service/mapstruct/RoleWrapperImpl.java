package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.Role;
import com.jeeplus.sys.service.dto.RoleDTO;
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
public class RoleWrapperImpl implements RoleWrapper {

    @Override
    public RoleDTO toDTO(Role arg0) {
        if ( arg0 == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setCreateBy( roleToUserDTO( arg0 ) );
        roleDTO.setUpdateBy( roleToUserDTO1( arg0 ) );
        roleDTO.setCreateDate( arg0.getCreateDate() );
        roleDTO.setDelFlag( arg0.getDelFlag() );
        roleDTO.setId( arg0.getId() );
        roleDTO.setUpdateDate( arg0.getUpdateDate() );
        roleDTO.setEnName( arg0.getEnName() );
        roleDTO.setName( arg0.getName() );
        roleDTO.setRemarks( arg0.getRemarks() );
        roleDTO.setSysData( arg0.getSysData() );
        roleDTO.setUseable( arg0.getUseable() );

        return roleDTO;
    }

    @Override
    public List<RoleDTO> toDTO(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDTO( role ) );
        }

        return list;
    }

    @Override
    public Page<RoleDTO> toDTO(Page<Role> page) {
        if ( page == null ) {
            return null;
        }

        Page<RoleDTO> page1 = new Page<RoleDTO>();

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
    public Role toEntity(RoleDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Role role = new Role();

        role.setCreateBy( arg0CreateById( arg0 ) );
        role.setUpdateBy( arg0UpdateById( arg0 ) );
        role.setCreateDate( arg0.getCreateDate() );
        role.setDelFlag( arg0.getDelFlag() );
        role.setId( arg0.getId() );
        role.setUpdateDate( arg0.getUpdateDate() );
        role.setEnName( arg0.getEnName() );
        role.setName( arg0.getName() );
        role.setRemarks( arg0.getRemarks() );
        role.setSysData( arg0.getSysData() );
        role.setUseable( arg0.getUseable() );

        return role;
    }

    @Override
    public List<Role> toEntity(List<RoleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleDTO roleDTO : dtoList ) {
            list.add( toEntity( roleDTO ) );
        }

        return list;
    }

    @Override
    public Page<Role> toEntity(Page<RoleDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Role> page1 = new Page<Role>();

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

    protected UserDTO roleToUserDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( role.getCreateBy() );

        return userDTO;
    }

    protected UserDTO roleToUserDTO1(Role role) {
        if ( role == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( role.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }
        UserDTO createBy = roleDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }
        UserDTO updateBy = roleDTO.getUpdateBy();
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
