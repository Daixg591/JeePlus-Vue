package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.Log;
import com.jeeplus.sys.service.dto.LogDTO;
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
public class LogWrapperImpl implements LogWrapper {

    @Override
    public LogDTO toDTO(Log arg0) {
        if ( arg0 == null ) {
            return null;
        }

        LogDTO logDTO = new LogDTO();

        logDTO.setCreateBy( logToUserDTO( arg0 ) );
        logDTO.setUpdateBy( logToUserDTO1( arg0 ) );
        logDTO.setDelFlag( arg0.getDelFlag() );
        logDTO.setId( arg0.getId() );
        logDTO.setUpdateDate( arg0.getUpdateDate() );
        logDTO.setCreateDate( arg0.getCreateDate() );
        logDTO.setException( arg0.getException() );
        logDTO.setMethod( arg0.getMethod() );
        logDTO.setParams( arg0.getParams() );
        logDTO.setRecordTime( arg0.getRecordTime() );
        logDTO.setRemarks( arg0.getRemarks() );
        logDTO.setRemoteAddr( arg0.getRemoteAddr() );
        logDTO.setRequestType( arg0.getRequestType() );
        logDTO.setRequestUri( arg0.getRequestUri() );
        logDTO.setResult( arg0.getResult() );
        logDTO.setTitle( arg0.getTitle() );
        logDTO.setType( arg0.getType() );
        logDTO.setUserAgent( arg0.getUserAgent() );

        return logDTO;
    }

    @Override
    public List<LogDTO> toDTO(List<Log> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LogDTO> list = new ArrayList<LogDTO>( entityList.size() );
        for ( Log log : entityList ) {
            list.add( toDTO( log ) );
        }

        return list;
    }

    @Override
    public Page<LogDTO> toDTO(Page<Log> page) {
        if ( page == null ) {
            return null;
        }

        Page<LogDTO> page1 = new Page<LogDTO>();

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
    public Log toEntity(LogDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Log log = new Log();

        log.setCreateBy( arg0CreateById( arg0 ) );
        log.setUpdateBy( arg0UpdateById( arg0 ) );
        log.setCreateDate( arg0.getCreateDate() );
        log.setDelFlag( arg0.getDelFlag() );
        log.setId( arg0.getId() );
        log.setException( arg0.getException() );
        log.setMethod( arg0.getMethod() );
        log.setParams( arg0.getParams() );
        log.setRecordTime( arg0.getRecordTime() );
        log.setRemarks( arg0.getRemarks() );
        log.setRemoteAddr( arg0.getRemoteAddr() );
        log.setRequestType( arg0.getRequestType() );
        log.setRequestUri( arg0.getRequestUri() );
        log.setResult( arg0.getResult() );
        log.setTitle( arg0.getTitle() );
        log.setType( arg0.getType() );
        log.setUpdateDate( arg0.getUpdateDate() );
        log.setUserAgent( arg0.getUserAgent() );

        return log;
    }

    @Override
    public List<Log> toEntity(List<LogDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Log> list = new ArrayList<Log>( dtoList.size() );
        for ( LogDTO logDTO : dtoList ) {
            list.add( toEntity( logDTO ) );
        }

        return list;
    }

    @Override
    public Page<Log> toEntity(Page<LogDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Log> page1 = new Page<Log>();

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

    protected UserDTO logToUserDTO(Log log) {
        if ( log == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( log.getCreateBy() );

        return userDTO;
    }

    protected UserDTO logToUserDTO1(Log log) {
        if ( log == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( log.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(LogDTO logDTO) {
        if ( logDTO == null ) {
            return null;
        }
        UserDTO createBy = logDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(LogDTO logDTO) {
        if ( logDTO == null ) {
            return null;
        }
        UserDTO updateBy = logDTO.getUpdateBy();
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
