package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.DataRule;
import com.jeeplus.sys.service.dto.DataRuleDTO;
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
public class DataRuleWrapperImpl implements DataRuleWrapper {

    @Override
    public DataRuleDTO toDTO(DataRule arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DataRuleDTO dataRuleDTO = new DataRuleDTO();

        dataRuleDTO.setCreateBy( dataRuleToUserDTO( arg0 ) );
        dataRuleDTO.setUpdateBy( dataRuleToUserDTO1( arg0 ) );
        dataRuleDTO.setCreateDate( arg0.getCreateDate() );
        dataRuleDTO.setDelFlag( arg0.getDelFlag() );
        dataRuleDTO.setId( arg0.getId() );
        dataRuleDTO.setUpdateDate( arg0.getUpdateDate() );
        dataRuleDTO.setClassName( arg0.getClassName() );
        dataRuleDTO.setExpress( arg0.getExpress() );
        dataRuleDTO.setField( arg0.getField() );
        dataRuleDTO.setMenuId( arg0.getMenuId() );
        dataRuleDTO.setName( arg0.getName() );
        dataRuleDTO.setRemarks( arg0.getRemarks() );
        dataRuleDTO.setSqlSegment( arg0.getSqlSegment() );
        dataRuleDTO.setValue( arg0.getValue() );

        return dataRuleDTO;
    }

    @Override
    public List<DataRuleDTO> toDTO(List<DataRule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataRuleDTO> list = new ArrayList<DataRuleDTO>( entityList.size() );
        for ( DataRule dataRule : entityList ) {
            list.add( toDTO( dataRule ) );
        }

        return list;
    }

    @Override
    public Page<DataRuleDTO> toDTO(Page<DataRule> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataRuleDTO> page1 = new Page<DataRuleDTO>();

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
    public DataRule toEntity(DataRuleDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DataRule dataRule = new DataRule();

        dataRule.setCreateBy( arg0CreateById( arg0 ) );
        dataRule.setUpdateBy( arg0UpdateById( arg0 ) );
        dataRule.setCreateDate( arg0.getCreateDate() );
        dataRule.setDelFlag( arg0.getDelFlag() );
        dataRule.setId( arg0.getId() );
        dataRule.setUpdateDate( arg0.getUpdateDate() );
        dataRule.setClassName( arg0.getClassName() );
        dataRule.setExpress( arg0.getExpress() );
        dataRule.setField( arg0.getField() );
        dataRule.setMenuId( arg0.getMenuId() );
        dataRule.setName( arg0.getName() );
        dataRule.setRemarks( arg0.getRemarks() );
        dataRule.setSqlSegment( arg0.getSqlSegment() );
        dataRule.setValue( arg0.getValue() );

        return dataRule;
    }

    @Override
    public List<DataRule> toEntity(List<DataRuleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataRule> list = new ArrayList<DataRule>( dtoList.size() );
        for ( DataRuleDTO dataRuleDTO : dtoList ) {
            list.add( toEntity( dataRuleDTO ) );
        }

        return list;
    }

    @Override
    public Page<DataRule> toEntity(Page<DataRuleDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataRule> page1 = new Page<DataRule>();

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

    protected UserDTO dataRuleToUserDTO(DataRule dataRule) {
        if ( dataRule == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataRule.getCreateBy() );

        return userDTO;
    }

    protected UserDTO dataRuleToUserDTO1(DataRule dataRule) {
        if ( dataRule == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataRule.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(DataRuleDTO dataRuleDTO) {
        if ( dataRuleDTO == null ) {
            return null;
        }
        UserDTO createBy = dataRuleDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(DataRuleDTO dataRuleDTO) {
        if ( dataRuleDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataRuleDTO.getUpdateBy();
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
