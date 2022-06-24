package com.jeeplus.notify.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.notify.domain.NotifyRecord;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.notify.service.dto.NotifyRecordDTO;
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
public class NotifyRecordWrapperImpl implements NotifyRecordWrapper {

    @Override
    public List<NotifyRecordDTO> toDTO(List<NotifyRecord> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NotifyRecordDTO> list = new ArrayList<NotifyRecordDTO>( entityList.size() );
        for ( NotifyRecord notifyRecord : entityList ) {
            list.add( toDTO( notifyRecord ) );
        }

        return list;
    }

    @Override
    public Page<NotifyRecordDTO> toDTO(Page<NotifyRecord> page) {
        if ( page == null ) {
            return null;
        }

        Page<NotifyRecordDTO> page1 = new Page<NotifyRecordDTO>();

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
    public List<NotifyRecord> toEntity(List<NotifyRecordDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<NotifyRecord> list = new ArrayList<NotifyRecord>( dtoList.size() );
        for ( NotifyRecordDTO notifyRecordDTO : dtoList ) {
            list.add( toEntity( notifyRecordDTO ) );
        }

        return list;
    }

    @Override
    public Page<NotifyRecord> toEntity(Page<NotifyRecordDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<NotifyRecord> page1 = new Page<NotifyRecord>();

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
    public NotifyRecord toEntity(NotifyRecordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NotifyRecord notifyRecord = new NotifyRecord();

        notifyRecord.setNotifyId( dtoNotifyDTOId( dto ) );
        notifyRecord.setUserId( dtoUserDTOId( dto ) );
        notifyRecord.setCreateBy( dtoCreateById( dto ) );
        notifyRecord.setUpdateBy( dtoUpdateById( dto ) );
        notifyRecord.setCreateDate( dto.getCreateDate() );
        notifyRecord.setDelFlag( dto.getDelFlag() );
        notifyRecord.setId( dto.getId() );
        notifyRecord.setUpdateDate( dto.getUpdateDate() );
        notifyRecord.setReadDate( dto.getReadDate() );
        notifyRecord.setReadFlag( dto.getReadFlag() );

        return notifyRecord;
    }

    @Override
    public NotifyRecordDTO toDTO(NotifyRecord entity) {
        if ( entity == null ) {
            return null;
        }

        NotifyRecordDTO notifyRecordDTO = new NotifyRecordDTO();

        notifyRecordDTO.setNotifyDTO( notifyRecordToNotifyDTO( entity ) );
        notifyRecordDTO.setUserDTO( notifyRecordToUserDTO( entity ) );
        notifyRecordDTO.setCreateBy( notifyRecordToUserDTO1( entity ) );
        notifyRecordDTO.setUpdateBy( notifyRecordToUserDTO2( entity ) );
        notifyRecordDTO.setCreateDate( entity.getCreateDate() );
        notifyRecordDTO.setDelFlag( entity.getDelFlag() );
        notifyRecordDTO.setId( entity.getId() );
        notifyRecordDTO.setUpdateDate( entity.getUpdateDate() );
        notifyRecordDTO.setReadDate( entity.getReadDate() );
        notifyRecordDTO.setReadFlag( entity.getReadFlag() );

        return notifyRecordDTO;
    }

    private String dtoNotifyDTOId(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        NotifyDTO notifyDTO = notifyRecordDTO.getNotifyDTO();
        if ( notifyDTO == null ) {
            return null;
        }
        String id = notifyDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUserDTOId(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        UserDTO userDTO = notifyRecordDTO.getUserDTO();
        if ( userDTO == null ) {
            return null;
        }
        String id = userDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        UserDTO createBy = notifyRecordDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        UserDTO updateBy = notifyRecordDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected NotifyDTO notifyRecordToNotifyDTO(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        NotifyDTO notifyDTO = new NotifyDTO();

        notifyDTO.setId( notifyRecord.getNotifyId() );

        return notifyDTO;
    }

    protected UserDTO notifyRecordToUserDTO(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notifyRecord.getUserId() );

        return userDTO;
    }

    protected UserDTO notifyRecordToUserDTO1(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notifyRecord.getCreateBy() );

        return userDTO;
    }

    protected UserDTO notifyRecordToUserDTO2(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notifyRecord.getUpdateBy() );

        return userDTO;
    }
}
