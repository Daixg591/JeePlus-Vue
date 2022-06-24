package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.sys.domain.Menu;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.service.dto.MenuDTO;
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
public class MenuWrapperImpl implements MenuWrapper {

    @Override
    public MenuDTO copyDTO(MenuDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setCreateBy( dto.getCreateBy() );
        menuDTO.setCreateDate( dto.getCreateDate() );
        menuDTO.setDelFlag( dto.getDelFlag() );
        menuDTO.setId( dto.getId() );
        menuDTO.setUpdateBy( dto.getUpdateBy() );
        menuDTO.setUpdateDate( dto.getUpdateDate() );
        List<MenuDTO> list = dto.getChildren();
        if ( list != null ) {
            menuDTO.setChildren( new ArrayList<MenuDTO>( list ) );
        }
        menuDTO.setName( dto.getName() );
        menuDTO.setParent( copyDTO( dto.getParent() ) );
        menuDTO.setParentIds( dto.getParentIds() );
        menuDTO.setSort( dto.getSort() );
        menuDTO.setAffix( dto.getAffix() );
        List<DataRuleDTO> list1 = dto.getDataRuleDTOList();
        if ( list1 != null ) {
            menuDTO.setDataRuleDTOList( new ArrayList<DataRuleDTO>( list1 ) );
        }
        menuDTO.setHref( dto.getHref() );
        menuDTO.setIcon( dto.getIcon() );
        menuDTO.setIsShow( dto.getIsShow() );
        menuDTO.setPermission( dto.getPermission() );
        menuDTO.setRemarks( dto.getRemarks() );
        menuDTO.setTarget( dto.getTarget() );
        menuDTO.setType( dto.getType() );
        menuDTO.setUserId( dto.getUserId() );

        return menuDTO;
    }

    @Override
    public Menu copyEntity(Menu entity) {
        if ( entity == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setCreateBy( entity.getCreateBy() );
        menu.setCreateDate( entity.getCreateDate() );
        menu.setDelFlag( entity.getDelFlag() );
        menu.setId( entity.getId() );
        menu.setUpdateBy( entity.getUpdateBy() );
        menu.setUpdateDate( entity.getUpdateDate() );
        List<Menu> list = entity.getChildren();
        if ( list != null ) {
            menu.setChildren( new ArrayList<Menu>( list ) );
        }
        menu.setName( entity.getName() );
        menu.setParentId( entity.getParentId() );
        menu.setParentIds( entity.getParentIds() );
        menu.setSort( entity.getSort() );
        menu.setAffix( entity.getAffix() );
        menu.setHref( entity.getHref() );
        menu.setIcon( entity.getIcon() );
        menu.setIsShow( entity.getIsShow() );
        menu.setPermission( entity.getPermission() );
        menu.setRemarks( entity.getRemarks() );
        menu.setTarget( entity.getTarget() );
        menu.setType( entity.getType() );

        return menu;
    }

    @Override
    public MenuDTO toDTO(Menu arg0) {
        if ( arg0 == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setParent( menuToMenuDTO( arg0 ) );
        menuDTO.setCreateBy( menuToUserDTO( arg0 ) );
        menuDTO.setUpdateBy( menuToUserDTO1( arg0 ) );
        menuDTO.setCreateDate( arg0.getCreateDate() );
        menuDTO.setDelFlag( arg0.getDelFlag() );
        menuDTO.setId( arg0.getId() );
        menuDTO.setUpdateDate( arg0.getUpdateDate() );
        menuDTO.setChildren( toDTO( arg0.getChildren() ) );
        menuDTO.setName( arg0.getName() );
        menuDTO.setParentIds( arg0.getParentIds() );
        menuDTO.setSort( arg0.getSort() );
        menuDTO.setAffix( arg0.getAffix() );
        menuDTO.setHref( arg0.getHref() );
        menuDTO.setIcon( arg0.getIcon() );
        menuDTO.setIsShow( arg0.getIsShow() );
        menuDTO.setPermission( arg0.getPermission() );
        menuDTO.setRemarks( arg0.getRemarks() );
        menuDTO.setTarget( arg0.getTarget() );
        menuDTO.setType( arg0.getType() );

        return menuDTO;
    }

    @Override
    public List<MenuDTO> toDTO(List<Menu> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MenuDTO> list = new ArrayList<MenuDTO>( entityList.size() );
        for ( Menu menu : entityList ) {
            list.add( toDTO( menu ) );
        }

        return list;
    }

    @Override
    public Menu toEntity(MenuDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setParentId( arg0ParentId( arg0 ) );
        menu.setCreateBy( arg0CreateById( arg0 ) );
        menu.setUpdateBy( arg0UpdateById( arg0 ) );
        menu.setCreateDate( arg0.getCreateDate() );
        menu.setDelFlag( arg0.getDelFlag() );
        menu.setId( arg0.getId() );
        menu.setUpdateDate( arg0.getUpdateDate() );
        menu.setChildren( toEntity( arg0.getChildren() ) );
        menu.setName( arg0.getName() );
        menu.setParentIds( arg0.getParentIds() );
        menu.setSort( arg0.getSort() );
        menu.setAffix( arg0.getAffix() );
        menu.setHref( arg0.getHref() );
        menu.setIcon( arg0.getIcon() );
        menu.setIsShow( arg0.getIsShow() );
        menu.setPermission( arg0.getPermission() );
        menu.setRemarks( arg0.getRemarks() );
        menu.setTarget( arg0.getTarget() );
        menu.setType( arg0.getType() );

        return menu;
    }

    @Override
    public List<Menu> toEntity(List<MenuDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Menu> list = new ArrayList<Menu>( dtoList.size() );
        for ( MenuDTO menuDTO : dtoList ) {
            list.add( toEntity( menuDTO ) );
        }

        return list;
    }

    protected MenuDTO menuToMenuDTO(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setId( menu.getParentId() );

        return menuDTO;
    }

    protected UserDTO menuToUserDTO(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( menu.getCreateBy() );

        return userDTO;
    }

    protected UserDTO menuToUserDTO1(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( menu.getUpdateBy() );

        return userDTO;
    }

    private String arg0ParentId(MenuDTO menuDTO) {
        if ( menuDTO == null ) {
            return null;
        }
        MenuDTO parent = menuDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0CreateById(MenuDTO menuDTO) {
        if ( menuDTO == null ) {
            return null;
        }
        UserDTO createBy = menuDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(MenuDTO menuDTO) {
        if ( menuDTO == null ) {
            return null;
        }
        UserDTO updateBy = menuDTO.getUpdateBy();
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
