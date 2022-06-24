package com.jeeplus.datav.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.datav.domain.DataMap;
import com.jeeplus.datav.service.dto.DataMapDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:52:45+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class DataMapWrapperImpl implements DataMapWrapper {

    @Override
    public DataMapDTO toDTO(DataMap arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DataMapDTO dataMapDTO = new DataMapDTO();

        dataMapDTO.setCreateBy( dataMapToUserDTO( arg0 ) );
        dataMapDTO.setUpdateBy( dataMapToUserDTO1( arg0 ) );
        dataMapDTO.setCreateDate( arg0.getCreateDate() );
        dataMapDTO.setDelFlag( arg0.getDelFlag() );
        dataMapDTO.setId( arg0.getId() );
        dataMapDTO.setUpdateDate( arg0.getUpdateDate() );
        dataMapDTO.setData( arg0.getData() );
        dataMapDTO.setName( arg0.getName() );
        dataMapDTO.setRemarks( arg0.getRemarks() );

        return dataMapDTO;
    }

    @Override
    public List<DataMapDTO> toDTO(List<DataMap> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataMapDTO> list = new ArrayList<DataMapDTO>( entityList.size() );
        for ( DataMap dataMap : entityList ) {
            list.add( toDTO( dataMap ) );
        }

        return list;
    }

    @Override
    public Page<DataMapDTO> toDTO(Page<DataMap> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataMapDTO> page1 = new Page<DataMapDTO>();

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
    public DataMap toEntity(DataMapDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DataMap dataMap = new DataMap();

        dataMap.setCreateBy( arg0CreateById( arg0 ) );
        dataMap.setUpdateBy( arg0UpdateById( arg0 ) );
        dataMap.setCreateDate( arg0.getCreateDate() );
        dataMap.setDelFlag( arg0.getDelFlag() );
        dataMap.setId( arg0.getId() );
        dataMap.setUpdateDate( arg0.getUpdateDate() );
        dataMap.setData( arg0.getData() );
        dataMap.setName( arg0.getName() );
        dataMap.setRemarks( arg0.getRemarks() );

        return dataMap;
    }

    @Override
    public List<DataMap> toEntity(List<DataMapDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataMap> list = new ArrayList<DataMap>( dtoList.size() );
        for ( DataMapDTO dataMapDTO : dtoList ) {
            list.add( toEntity( dataMapDTO ) );
        }

        return list;
    }

    @Override
    public Page<DataMap> toEntity(Page<DataMapDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataMap> page1 = new Page<DataMap>();

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

    protected UserDTO dataMapToUserDTO(DataMap dataMap) {
        if ( dataMap == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataMap.getCreateBy() );

        return userDTO;
    }

    protected UserDTO dataMapToUserDTO1(DataMap dataMap) {
        if ( dataMap == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataMap.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(DataMapDTO dataMapDTO) {
        if ( dataMapDTO == null ) {
            return null;
        }
        UserDTO createBy = dataMapDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(DataMapDTO dataMapDTO) {
        if ( dataMapDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataMapDTO.getUpdateBy();
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
