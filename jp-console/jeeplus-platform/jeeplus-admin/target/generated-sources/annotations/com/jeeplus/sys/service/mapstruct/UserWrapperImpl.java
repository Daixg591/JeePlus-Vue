package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:52:35+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class UserWrapperImpl implements UserWrapper {

    @Override
    public List<UserDTO> toDTO(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public Page<UserDTO> toDTO(Page<User> page) {
        if ( page == null ) {
            return null;
        }

        Page<UserDTO> page1 = new Page<UserDTO>();

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
    public List<User> toEntity(List<UserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDTO userDTO : dtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public Page<User> toEntity(Page<UserDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<User> page1 = new Page<User>();

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
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        String id = null;

        id = dto.getId();

        User user = new User( id );

        user.setCompanyId( dtoCompanyDTOId( dto ) );
        user.setOfficeId( dtoOfficeDTOId( dto ) );
        user.setCreateBy( dtoCreateById( dto ) );
        user.setUpdateBy( dtoUpdateById( dto ) );
        user.setCreateDate( dto.getCreateDate() );
        user.setDelFlag( dto.getDelFlag() );
        user.setUpdateDate( dto.getUpdateDate() );
        user.setEmail( dto.getEmail() );
        user.setIsAdmin( dto.getIsAdmin() );
        user.setLoginDate( dto.getLoginDate() );
        user.setLoginFlag( dto.getLoginFlag() );
        user.setLoginIp( dto.getLoginIp() );
        user.setLoginName( dto.getLoginName() );
        user.setMobile( dto.getMobile() );
        user.setName( dto.getName() );
        user.setNo( dto.getNo() );
        user.setPassword( dto.getPassword() );
        user.setPhone( dto.getPhone() );
        user.setPhoto( dto.getPhoto() );
        user.setQrCode( dto.getQrCode() );
        user.setRemarks( dto.getRemarks() );
        user.setSign( dto.getSign() );

        return user;
    }

    @Override
    public UserDTO toDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setCompanyDTO( userToOfficeDTO( entity ) );
        userDTO.setOfficeDTO( userToOfficeDTO1( entity ) );
        userDTO.setCreateBy( userToUserDTO( entity ) );
        userDTO.setUpdateBy( userToUserDTO1( entity ) );
        userDTO.setCreateDate( entity.getCreateDate() );
        userDTO.setDelFlag( entity.getDelFlag() );
        userDTO.setId( entity.getId() );
        userDTO.setUpdateDate( entity.getUpdateDate() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setIsAdmin( entity.getIsAdmin() );
        userDTO.setLoginDate( entity.getLoginDate() );
        userDTO.setLoginFlag( entity.getLoginFlag() );
        userDTO.setLoginIp( entity.getLoginIp() );
        userDTO.setLoginName( entity.getLoginName() );
        userDTO.setMobile( entity.getMobile() );
        userDTO.setName( entity.getName() );
        userDTO.setNo( entity.getNo() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setPhone( entity.getPhone() );
        userDTO.setPhoto( entity.getPhoto() );
        userDTO.setQrCode( entity.getQrCode() );
        userDTO.setRemarks( entity.getRemarks() );
        userDTO.setSign( entity.getSign() );

        return userDTO;
    }

    private String dtoCompanyDTOId(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        OfficeDTO companyDTO = userDTO.getCompanyDTO();
        if ( companyDTO == null ) {
            return null;
        }
        String id = companyDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoOfficeDTOId(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        OfficeDTO officeDTO = userDTO.getOfficeDTO();
        if ( officeDTO == null ) {
            return null;
        }
        String id = officeDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        UserDTO createBy = userDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        UserDTO updateBy = userDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected OfficeDTO userToOfficeDTO(User user) {
        if ( user == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( user.getCompanyId() );

        return officeDTO;
    }

    protected OfficeDTO userToOfficeDTO1(User user) {
        if ( user == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( user.getOfficeId() );

        return officeDTO;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getCreateBy() );

        return userDTO;
    }

    protected UserDTO userToUserDTO1(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getUpdateBy() );

        return userDTO;
    }
}
