package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.DictValue;
import com.jeeplus.sys.service.dto.DictTypeDTO;
import com.jeeplus.sys.service.dto.DictValueDTO;
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
public class DictValueWrapperImpl implements DictValueWrapper {

    @Override
    public DictValueDTO toDTO(DictValue arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DictTypeDTO dictTypeDTO = null;

        DictValueDTO dictValueDTO = new DictValueDTO( dictTypeDTO );

        dictValueDTO.setCreateBy( dictValueToUserDTO( arg0 ) );
        dictValueDTO.setUpdateBy( dictValueToUserDTO1( arg0 ) );
        dictValueDTO.setCreateDate( arg0.getCreateDate() );
        dictValueDTO.setDelFlag( arg0.getDelFlag() );
        dictValueDTO.setId( arg0.getId() );
        dictValueDTO.setUpdateDate( arg0.getUpdateDate() );
        dictValueDTO.setLabel( arg0.getLabel() );
        dictValueDTO.setSort( arg0.getSort() );
        dictValueDTO.setValue( arg0.getValue() );

        return dictValueDTO;
    }

    @Override
    public List<DictValueDTO> toDTO(List<DictValue> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictValueDTO> list = new ArrayList<DictValueDTO>( entityList.size() );
        for ( DictValue dictValue : entityList ) {
            list.add( toDTO( dictValue ) );
        }

        return list;
    }

    @Override
    public Page<DictValueDTO> toDTO(Page<DictValue> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictValueDTO> page1 = new Page<DictValueDTO>();

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
    public DictValue toEntity(DictValueDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DictValue dictValue = new DictValue();

        dictValue.setCreateBy( arg0CreateById( arg0 ) );
        dictValue.setUpdateBy( arg0UpdateById( arg0 ) );
        dictValue.setCreateDate( arg0.getCreateDate() );
        dictValue.setDelFlag( arg0.getDelFlag() );
        dictValue.setId( arg0.getId() );
        dictValue.setUpdateDate( arg0.getUpdateDate() );
        dictValue.setLabel( arg0.getLabel() );
        dictValue.setSort( arg0.getSort() );
        dictValue.setValue( arg0.getValue() );

        return dictValue;
    }

    @Override
    public List<DictValue> toEntity(List<DictValueDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DictValue> list = new ArrayList<DictValue>( dtoList.size() );
        for ( DictValueDTO dictValueDTO : dtoList ) {
            list.add( toEntity( dictValueDTO ) );
        }

        return list;
    }

    @Override
    public Page<DictValue> toEntity(Page<DictValueDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictValue> page1 = new Page<DictValue>();

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

    protected UserDTO dictValueToUserDTO(DictValue dictValue) {
        if ( dictValue == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dictValue.getCreateBy() );

        return userDTO;
    }

    protected UserDTO dictValueToUserDTO1(DictValue dictValue) {
        if ( dictValue == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dictValue.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(DictValueDTO dictValueDTO) {
        if ( dictValueDTO == null ) {
            return null;
        }
        UserDTO createBy = dictValueDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(DictValueDTO dictValueDTO) {
        if ( dictValueDTO == null ) {
            return null;
        }
        UserDTO updateBy = dictValueDTO.getUpdateBy();
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
