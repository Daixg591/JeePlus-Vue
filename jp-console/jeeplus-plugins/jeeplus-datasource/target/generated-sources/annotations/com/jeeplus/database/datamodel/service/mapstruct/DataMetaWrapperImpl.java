package com.jeeplus.database.datamodel.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.service.dto.DataMetaDTO;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:52:43+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class DataMetaWrapperImpl implements DataMetaWrapper {

    @Override
    public List<DataMetaDTO> toDTO(List<DataMeta> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataMetaDTO> list = new ArrayList<DataMetaDTO>( entityList.size() );
        for ( DataMeta dataMeta : entityList ) {
            list.add( toDTO( dataMeta ) );
        }

        return list;
    }

    @Override
    public Page<DataMetaDTO> toDTO(Page<DataMeta> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataMetaDTO> page1 = new Page<DataMetaDTO>();

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
    public List<DataMeta> toEntity(List<DataMetaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataMeta> list = new ArrayList<DataMeta>( dtoList.size() );
        for ( DataMetaDTO dataMetaDTO : dtoList ) {
            list.add( toEntity( dataMetaDTO ) );
        }

        return list;
    }

    @Override
    public Page<DataMeta> toEntity(Page<DataMetaDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataMeta> page1 = new Page<DataMeta>();

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
    public DataMeta toEntity(DataMetaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataMeta dataMeta = new DataMeta();

        dataMeta.setDataSetId( dtoDataSetDTOId( dto ) );
        dataMeta.setCreateBy( dtoCreateById( dto ) );
        dataMeta.setUpdateBy( dtoUpdateById( dto ) );
        dataMeta.setCreateDate( dto.getCreateDate() );
        dataMeta.setDelFlag( dto.getDelFlag() );
        dataMeta.setId( dto.getId() );
        dataMeta.setUpdateDate( dto.getUpdateDate() );
        dataMeta.setIsNeed( dto.getIsNeed() );
        dataMeta.setLabel( dto.getLabel() );
        dataMeta.setName( dto.getName() );
        dataMeta.setSort( dto.getSort() );
        dataMeta.setType( dto.getType() );

        return dataMeta;
    }

    @Override
    public DataMetaDTO toDTO(DataMeta entity) {
        if ( entity == null ) {
            return null;
        }

        DataMetaDTO dataMetaDTO = new DataMetaDTO();

        dataMetaDTO.setDataSetDTO( dataMetaToDataSetDTO( entity ) );
        dataMetaDTO.setCreateBy( dataMetaToUserDTO( entity ) );
        dataMetaDTO.setUpdateBy( dataMetaToUserDTO1( entity ) );
        dataMetaDTO.setCreateDate( entity.getCreateDate() );
        dataMetaDTO.setDelFlag( entity.getDelFlag() );
        dataMetaDTO.setId( entity.getId() );
        dataMetaDTO.setUpdateDate( entity.getUpdateDate() );
        dataMetaDTO.setIsNeed( entity.getIsNeed() );
        dataMetaDTO.setLabel( entity.getLabel() );
        dataMetaDTO.setName( entity.getName() );
        dataMetaDTO.setSort( entity.getSort() );
        dataMetaDTO.setType( entity.getType() );

        return dataMetaDTO;
    }

    private String dtoDataSetDTOId(DataMetaDTO dataMetaDTO) {
        if ( dataMetaDTO == null ) {
            return null;
        }
        DataSetDTO dataSetDTO = dataMetaDTO.getDataSetDTO();
        if ( dataSetDTO == null ) {
            return null;
        }
        String id = dataSetDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(DataMetaDTO dataMetaDTO) {
        if ( dataMetaDTO == null ) {
            return null;
        }
        UserDTO createBy = dataMetaDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DataMetaDTO dataMetaDTO) {
        if ( dataMetaDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataMetaDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected DataSetDTO dataMetaToDataSetDTO(DataMeta dataMeta) {
        if ( dataMeta == null ) {
            return null;
        }

        DataSetDTO dataSetDTO = new DataSetDTO();

        dataSetDTO.setId( dataMeta.getDataSetId() );

        return dataSetDTO;
    }

    protected UserDTO dataMetaToUserDTO(DataMeta dataMeta) {
        if ( dataMeta == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataMeta.getCreateBy() );

        return userDTO;
    }

    protected UserDTO dataMetaToUserDTO1(DataMeta dataMeta) {
        if ( dataMeta == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataMeta.getUpdateBy() );

        return userDTO;
    }
}
