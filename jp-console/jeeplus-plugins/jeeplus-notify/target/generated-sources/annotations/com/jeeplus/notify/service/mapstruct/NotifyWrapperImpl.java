package com.jeeplus.notify.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.notify.domain.Notify;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:53:00+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class NotifyWrapperImpl implements NotifyWrapper {

    @Override
    public NotifyDTO toDTO(Notify arg0) {
        if ( arg0 == null ) {
            return null;
        }

        NotifyDTO notifyDTO = new NotifyDTO();

        notifyDTO.setCreateBy( notifyToUserDTO( arg0 ) );
        notifyDTO.setUpdateBy( notifyToUserDTO1( arg0 ) );
        notifyDTO.setCreateDate( arg0.getCreateDate() );
        notifyDTO.setDelFlag( arg0.getDelFlag() );
        notifyDTO.setId( arg0.getId() );
        notifyDTO.setUpdateDate( arg0.getUpdateDate() );
        notifyDTO.setContent( arg0.getContent() );
        notifyDTO.setFiles( arg0.getFiles() );
        notifyDTO.setStatus( arg0.getStatus() );
        notifyDTO.setTitle( arg0.getTitle() );
        notifyDTO.setType( arg0.getType() );

        return notifyDTO;
    }

    @Override
    public List<NotifyDTO> toDTO(List<Notify> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NotifyDTO> list = new ArrayList<NotifyDTO>( entityList.size() );
        for ( Notify notify : entityList ) {
            list.add( toDTO( notify ) );
        }

        return list;
    }

    @Override
    public Page<NotifyDTO> toDTO(Page<Notify> page) {
        if ( page == null ) {
            return null;
        }

        Page<NotifyDTO> page1 = new Page<NotifyDTO>();

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
    public Notify toEntity(NotifyDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Notify notify = new Notify();

        notify.setCreateBy( arg0CreateById( arg0 ) );
        notify.setUpdateBy( arg0UpdateById( arg0 ) );
        notify.setCreateDate( arg0.getCreateDate() );
        notify.setDelFlag( arg0.getDelFlag() );
        notify.setId( arg0.getId() );
        notify.setUpdateDate( arg0.getUpdateDate() );
        notify.setContent( arg0.getContent() );
        notify.setFiles( arg0.getFiles() );
        notify.setStatus( arg0.getStatus() );
        notify.setTitle( arg0.getTitle() );
        notify.setType( arg0.getType() );

        return notify;
    }

    @Override
    public List<Notify> toEntity(List<NotifyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Notify> list = new ArrayList<Notify>( dtoList.size() );
        for ( NotifyDTO notifyDTO : dtoList ) {
            list.add( toEntity( notifyDTO ) );
        }

        return list;
    }

    @Override
    public Page<Notify> toEntity(Page<NotifyDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Notify> page1 = new Page<Notify>();

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

    protected UserDTO notifyToUserDTO(Notify notify) {
        if ( notify == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notify.getCreateBy() );

        return userDTO;
    }

    protected UserDTO notifyToUserDTO1(Notify notify) {
        if ( notify == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notify.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(NotifyDTO notifyDTO) {
        if ( notifyDTO == null ) {
            return null;
        }
        UserDTO createBy = notifyDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(NotifyDTO notifyDTO) {
        if ( notifyDTO == null ) {
            return null;
        }
        UserDTO updateBy = notifyDTO.getUpdateBy();
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
