package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.sys.domain.Area;
import com.jeeplus.sys.service.dto.AreaDTO;
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
public class AreaWrapperImpl implements AreaWrapper {

    @Override
    public AreaDTO copyDTO(AreaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AreaDTO areaDTO = new AreaDTO();

        areaDTO.setCreateBy( dto.getCreateBy() );
        areaDTO.setCreateDate( dto.getCreateDate() );
        areaDTO.setDelFlag( dto.getDelFlag() );
        areaDTO.setId( dto.getId() );
        areaDTO.setUpdateBy( dto.getUpdateBy() );
        areaDTO.setUpdateDate( dto.getUpdateDate() );
        List<AreaDTO> list = dto.getChildren();
        if ( list != null ) {
            areaDTO.setChildren( new ArrayList<AreaDTO>( list ) );
        }
        areaDTO.setName( dto.getName() );
        areaDTO.setParent( copyDTO( dto.getParent() ) );
        areaDTO.setParentIds( dto.getParentIds() );
        areaDTO.setSort( dto.getSort() );
        areaDTO.setCode( dto.getCode() );
        areaDTO.setRemarks( dto.getRemarks() );
        areaDTO.setType( dto.getType() );

        return areaDTO;
    }

    @Override
    public Area copyEntity(Area entity) {
        if ( entity == null ) {
            return null;
        }

        Area area = new Area();

        area.setCreateBy( entity.getCreateBy() );
        area.setCreateDate( entity.getCreateDate() );
        area.setDelFlag( entity.getDelFlag() );
        area.setId( entity.getId() );
        area.setUpdateBy( entity.getUpdateBy() );
        area.setUpdateDate( entity.getUpdateDate() );
        List<Area> list = entity.getChildren();
        if ( list != null ) {
            area.setChildren( new ArrayList<Area>( list ) );
        }
        area.setName( entity.getName() );
        area.setParentId( entity.getParentId() );
        area.setParentIds( entity.getParentIds() );
        area.setSort( entity.getSort() );
        area.setCode( entity.getCode() );
        area.setRemarks( entity.getRemarks() );
        area.setType( entity.getType() );

        return area;
    }

    @Override
    public AreaDTO toDTO(Area arg0) {
        if ( arg0 == null ) {
            return null;
        }

        AreaDTO areaDTO = new AreaDTO();

        areaDTO.setParent( areaToAreaDTO( arg0 ) );
        areaDTO.setCreateBy( areaToUserDTO( arg0 ) );
        areaDTO.setUpdateBy( areaToUserDTO1( arg0 ) );
        areaDTO.setCreateDate( arg0.getCreateDate() );
        areaDTO.setDelFlag( arg0.getDelFlag() );
        areaDTO.setId( arg0.getId() );
        areaDTO.setUpdateDate( arg0.getUpdateDate() );
        areaDTO.setChildren( toDTO( arg0.getChildren() ) );
        areaDTO.setName( arg0.getName() );
        areaDTO.setParentIds( arg0.getParentIds() );
        areaDTO.setSort( arg0.getSort() );
        areaDTO.setCode( arg0.getCode() );
        areaDTO.setRemarks( arg0.getRemarks() );
        areaDTO.setType( arg0.getType() );

        return areaDTO;
    }

    @Override
    public List<AreaDTO> toDTO(List<Area> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AreaDTO> list = new ArrayList<AreaDTO>( entityList.size() );
        for ( Area area : entityList ) {
            list.add( toDTO( area ) );
        }

        return list;
    }

    @Override
    public Area toEntity(AreaDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Area area = new Area();

        area.setParentId( arg0ParentId( arg0 ) );
        area.setCreateBy( arg0CreateById( arg0 ) );
        area.setUpdateBy( arg0UpdateById( arg0 ) );
        area.setCreateDate( arg0.getCreateDate() );
        area.setDelFlag( arg0.getDelFlag() );
        area.setId( arg0.getId() );
        area.setUpdateDate( arg0.getUpdateDate() );
        area.setChildren( toEntity( arg0.getChildren() ) );
        area.setName( arg0.getName() );
        area.setParentIds( arg0.getParentIds() );
        area.setSort( arg0.getSort() );
        area.setCode( arg0.getCode() );
        area.setRemarks( arg0.getRemarks() );
        area.setType( arg0.getType() );

        return area;
    }

    @Override
    public List<Area> toEntity(List<AreaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Area> list = new ArrayList<Area>( dtoList.size() );
        for ( AreaDTO areaDTO : dtoList ) {
            list.add( toEntity( areaDTO ) );
        }

        return list;
    }

    protected AreaDTO areaToAreaDTO(Area area) {
        if ( area == null ) {
            return null;
        }

        AreaDTO areaDTO = new AreaDTO();

        areaDTO.setId( area.getParentId() );

        return areaDTO;
    }

    protected UserDTO areaToUserDTO(Area area) {
        if ( area == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( area.getCreateBy() );

        return userDTO;
    }

    protected UserDTO areaToUserDTO1(Area area) {
        if ( area == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( area.getUpdateBy() );

        return userDTO;
    }

    private String arg0ParentId(AreaDTO areaDTO) {
        if ( areaDTO == null ) {
            return null;
        }
        AreaDTO parent = areaDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0CreateById(AreaDTO areaDTO) {
        if ( areaDTO == null ) {
            return null;
        }
        UserDTO createBy = areaDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(AreaDTO areaDTO) {
        if ( areaDTO == null ) {
            return null;
        }
        UserDTO updateBy = areaDTO.getUpdateBy();
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
