package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.DictType;
import com.jeeplus.sys.service.dto.DictTypeDTO;
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
public class DictTypeWrapperImpl implements DictTypeWrapper {

    @Override
    public DictTypeDTO toDTO(DictType arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DictTypeDTO dictTypeDTO = new DictTypeDTO();

        dictTypeDTO.setCreateBy( dictTypeToUserDTO( arg0 ) );
        dictTypeDTO.setUpdateBy( dictTypeToUserDTO1( arg0 ) );
        dictTypeDTO.setCreateDate( arg0.getCreateDate() );
        dictTypeDTO.setDelFlag( arg0.getDelFlag() );
        dictTypeDTO.setId( arg0.getId() );
        dictTypeDTO.setUpdateDate( arg0.getUpdateDate() );
        dictTypeDTO.setRemarks( arg0.getRemarks() );
        dictTypeDTO.setType( arg0.getType() );

        return dictTypeDTO;
    }

    @Override
    public List<DictTypeDTO> toDTO(List<DictType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictTypeDTO> list = new ArrayList<DictTypeDTO>( entityList.size() );
        for ( DictType dictType : entityList ) {
            list.add( toDTO( dictType ) );
        }

        return list;
    }

    @Override
    public Page<DictTypeDTO> toDTO(Page<DictType> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictTypeDTO> page1 = new Page<DictTypeDTO>();

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
    public DictType toEntity(DictTypeDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DictType dictType = new DictType();

        dictType.setCreateBy( arg0CreateById( arg0 ) );
        dictType.setUpdateBy( arg0UpdateById( arg0 ) );
        dictType.setCreateDate( arg0.getCreateDate() );
        dictType.setDelFlag( arg0.getDelFlag() );
        dictType.setId( arg0.getId() );
        dictType.setUpdateDate( arg0.getUpdateDate() );
        dictType.setRemarks( arg0.getRemarks() );
        dictType.setType( arg0.getType() );

        return dictType;
    }

    @Override
    public List<DictType> toEntity(List<DictTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DictType> list = new ArrayList<DictType>( dtoList.size() );
        for ( DictTypeDTO dictTypeDTO : dtoList ) {
            list.add( toEntity( dictTypeDTO ) );
        }

        return list;
    }

    @Override
    public Page<DictType> toEntity(Page<DictTypeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictType> page1 = new Page<DictType>();

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

    protected UserDTO dictTypeToUserDTO(DictType dictType) {
        if ( dictType == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dictType.getCreateBy() );

        return userDTO;
    }

    protected UserDTO dictTypeToUserDTO1(DictType dictType) {
        if ( dictType == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dictType.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(DictTypeDTO dictTypeDTO) {
        if ( dictTypeDTO == null ) {
            return null;
        }
        UserDTO createBy = dictTypeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(DictTypeDTO dictTypeDTO) {
        if ( dictTypeDTO == null ) {
            return null;
        }
        UserDTO updateBy = dictTypeDTO.getUpdateBy();
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
