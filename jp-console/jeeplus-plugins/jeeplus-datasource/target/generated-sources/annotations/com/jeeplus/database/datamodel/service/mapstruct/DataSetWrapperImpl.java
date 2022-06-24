package com.jeeplus.database.datamodel.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.database.datamodel.domain.DataSet;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:52:42+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class DataSetWrapperImpl implements DataSetWrapper {

    @Override
    public List<DataSetDTO> toDTO(List<DataSet> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataSetDTO> list = new ArrayList<DataSetDTO>( entityList.size() );
        for ( DataSet dataSet : entityList ) {
            list.add( toDTO( dataSet ) );
        }

        return list;
    }

    @Override
    public Page<DataSetDTO> toDTO(Page<DataSet> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataSetDTO> page1 = new Page<DataSetDTO>();

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
    public List<DataSet> toEntity(List<DataSetDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataSet> list = new ArrayList<DataSet>( dtoList.size() );
        for ( DataSetDTO dataSetDTO : dtoList ) {
            list.add( toEntity( dataSetDTO ) );
        }

        return list;
    }

    @Override
    public Page<DataSet> toEntity(Page<DataSetDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataSet> page1 = new Page<DataSet>();

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

    @Override
    public DataSet toEntity(DataSetDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataSet dataSet = new DataSet();

        dataSet.setDataSourceId( dtoDataSourceId( dto ) );
        dataSet.setCreateBy( dto.getCreateBy() );
        dataSet.setCreateDate( dto.getCreateDate() );
        dataSet.setDelFlag( dto.getDelFlag() );
        dataSet.setId( dto.getId() );
        dataSet.setUpdateBy( dto.getUpdateBy() );
        dataSet.setUpdateDate( dto.getUpdateDate() );
        dataSet.setName( dto.getName() );
        dataSet.setSqlCmd( dto.getSqlCmd() );

        return dataSet;
    }

    @Override
    public DataSetDTO toDTO(DataSet entity) {
        if ( entity == null ) {
            return null;
        }

        DataSetDTO dataSetDTO = new DataSetDTO();

        dataSetDTO.setDataSource( dataSetToDataSource( entity ) );
        dataSetDTO.setCreateBy( entity.getCreateBy() );
        dataSetDTO.setCreateDate( entity.getCreateDate() );
        dataSetDTO.setDelFlag( entity.getDelFlag() );
        dataSetDTO.setId( entity.getId() );
        dataSetDTO.setUpdateBy( entity.getUpdateBy() );
        dataSetDTO.setUpdateDate( entity.getUpdateDate() );
        dataSetDTO.setName( entity.getName() );
        dataSetDTO.setSqlCmd( entity.getSqlCmd() );

        return dataSetDTO;
    }

    private String dtoDataSourceId(DataSetDTO dataSetDTO) {
        if ( dataSetDTO == null ) {
            return null;
        }
        DataSource dataSource = dataSetDTO.getDataSource();
        if ( dataSource == null ) {
            return null;
        }
        String id = dataSource.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected DataSource dataSetToDataSource(DataSet dataSet) {
        if ( dataSet == null ) {
            return null;
        }

        DataSource dataSource = new DataSource();

        dataSource.setId( dataSet.getDataSourceId() );

        return dataSource;
    }
}
