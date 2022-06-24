package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.sys.domain.Office;
import com.jeeplus.sys.service.dto.AreaDTO;
import com.jeeplus.sys.service.dto.OfficeDTO;
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
public class OfficeWrapperImpl implements OfficeWrapper {

    @Override
    public OfficeDTO copyDTO(OfficeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setCreateBy( dto.getCreateBy() );
        officeDTO.setCreateDate( dto.getCreateDate() );
        officeDTO.setDelFlag( dto.getDelFlag() );
        officeDTO.setId( dto.getId() );
        officeDTO.setUpdateBy( dto.getUpdateBy() );
        officeDTO.setUpdateDate( dto.getUpdateDate() );
        List<OfficeDTO> list = dto.getChildren();
        if ( list != null ) {
            officeDTO.setChildren( new ArrayList<OfficeDTO>( list ) );
        }
        officeDTO.setName( dto.getName() );
        officeDTO.setParent( copyDTO( dto.getParent() ) );
        officeDTO.setParentIds( dto.getParentIds() );
        officeDTO.setSort( dto.getSort() );
        officeDTO.setAddress( dto.getAddress() );
        officeDTO.setAreaDTO( dto.getAreaDTO() );
        List<String> list1 = dto.getChildDeptList();
        if ( list1 != null ) {
            officeDTO.setChildDeptList( new ArrayList<String>( list1 ) );
        }
        officeDTO.setCode( dto.getCode() );
        officeDTO.setDeputyPerson( dto.getDeputyPerson() );
        officeDTO.setDisabled( dto.isDisabled() );
        officeDTO.setEmail( dto.getEmail() );
        officeDTO.setFax( dto.getFax() );
        officeDTO.setGrade( dto.getGrade() );
        officeDTO.setMaster( dto.getMaster() );
        officeDTO.setPhone( dto.getPhone() );
        officeDTO.setPrimaryPerson( dto.getPrimaryPerson() );
        officeDTO.setRemarks( dto.getRemarks() );
        officeDTO.setType( dto.getType() );
        officeDTO.setUseable( dto.getUseable() );
        officeDTO.setZipCode( dto.getZipCode() );

        return officeDTO;
    }

    @Override
    public Office copyEntity(Office entity) {
        if ( entity == null ) {
            return null;
        }

        Office office = new Office();

        office.setCreateBy( entity.getCreateBy() );
        office.setCreateDate( entity.getCreateDate() );
        office.setDelFlag( entity.getDelFlag() );
        office.setId( entity.getId() );
        office.setUpdateBy( entity.getUpdateBy() );
        office.setUpdateDate( entity.getUpdateDate() );
        List<Office> list = entity.getChildren();
        if ( list != null ) {
            office.setChildren( new ArrayList<Office>( list ) );
        }
        office.setName( entity.getName() );
        office.setParentId( entity.getParentId() );
        office.setParentIds( entity.getParentIds() );
        office.setSort( entity.getSort() );
        office.setAddress( entity.getAddress() );
        office.setAreaId( entity.getAreaId() );
        office.setCode( entity.getCode() );
        office.setDeputyPerson( entity.getDeputyPerson() );
        office.setEmail( entity.getEmail() );
        office.setFax( entity.getFax() );
        office.setGrade( entity.getGrade() );
        office.setMaster( entity.getMaster() );
        office.setPhone( entity.getPhone() );
        office.setPrimaryPerson( entity.getPrimaryPerson() );
        office.setRemarks( entity.getRemarks() );
        office.setType( entity.getType() );
        office.setUseable( entity.getUseable() );
        office.setZipCode( entity.getZipCode() );

        return office;
    }

    @Override
    public List<OfficeDTO> toDTO(List<Office> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OfficeDTO> list = new ArrayList<OfficeDTO>( entityList.size() );
        for ( Office office : entityList ) {
            list.add( toDTO( office ) );
        }

        return list;
    }

    @Override
    public List<Office> toEntity(List<OfficeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Office> list = new ArrayList<Office>( dtoList.size() );
        for ( OfficeDTO officeDTO : dtoList ) {
            list.add( toEntity( officeDTO ) );
        }

        return list;
    }

    @Override
    public Office toEntity(OfficeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Office office = new Office();

        office.setAreaId( dtoAreaDTOId( dto ) );
        office.setParentId( dtoParentId( dto ) );
        office.setCreateBy( dtoCreateById( dto ) );
        office.setUpdateBy( dtoUpdateById( dto ) );
        office.setPrimaryPerson( dtoPrimaryPersonId( dto ) );
        office.setDeputyPerson( dtoDeputyPersonId( dto ) );
        office.setCreateDate( dto.getCreateDate() );
        office.setDelFlag( dto.getDelFlag() );
        office.setId( dto.getId() );
        office.setUpdateDate( dto.getUpdateDate() );
        office.setChildren( toEntity( dto.getChildren() ) );
        office.setName( dto.getName() );
        office.setParentIds( dto.getParentIds() );
        office.setSort( dto.getSort() );
        office.setAddress( dto.getAddress() );
        office.setCode( dto.getCode() );
        office.setEmail( dto.getEmail() );
        office.setFax( dto.getFax() );
        office.setGrade( dto.getGrade() );
        office.setMaster( dto.getMaster() );
        office.setPhone( dto.getPhone() );
        office.setRemarks( dto.getRemarks() );
        office.setType( dto.getType() );
        office.setUseable( dto.getUseable() );
        office.setZipCode( dto.getZipCode() );

        return office;
    }

    @Override
    public OfficeDTO toDTO(Office entity) {
        if ( entity == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setAreaDTO( officeToAreaDTO( entity ) );
        officeDTO.setParent( officeToOfficeDTO( entity ) );
        officeDTO.setCreateBy( officeToUserDTO( entity ) );
        officeDTO.setUpdateBy( officeToUserDTO1( entity ) );
        officeDTO.setPrimaryPerson( officeToUserDTO2( entity ) );
        officeDTO.setDeputyPerson( officeToUserDTO3( entity ) );
        officeDTO.setCreateDate( entity.getCreateDate() );
        officeDTO.setDelFlag( entity.getDelFlag() );
        officeDTO.setId( entity.getId() );
        officeDTO.setUpdateDate( entity.getUpdateDate() );
        officeDTO.setChildren( toDTO( entity.getChildren() ) );
        officeDTO.setName( entity.getName() );
        officeDTO.setParentIds( entity.getParentIds() );
        officeDTO.setSort( entity.getSort() );
        officeDTO.setAddress( entity.getAddress() );
        officeDTO.setCode( entity.getCode() );
        officeDTO.setEmail( entity.getEmail() );
        officeDTO.setFax( entity.getFax() );
        officeDTO.setGrade( entity.getGrade() );
        officeDTO.setMaster( entity.getMaster() );
        officeDTO.setPhone( entity.getPhone() );
        officeDTO.setRemarks( entity.getRemarks() );
        officeDTO.setType( entity.getType() );
        officeDTO.setUseable( entity.getUseable() );
        officeDTO.setZipCode( entity.getZipCode() );

        return officeDTO;
    }

    private String dtoAreaDTOId(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        AreaDTO areaDTO = officeDTO.getAreaDTO();
        if ( areaDTO == null ) {
            return null;
        }
        String id = areaDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoParentId(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        OfficeDTO parent = officeDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        UserDTO createBy = officeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        UserDTO updateBy = officeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoPrimaryPersonId(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        UserDTO primaryPerson = officeDTO.getPrimaryPerson();
        if ( primaryPerson == null ) {
            return null;
        }
        String id = primaryPerson.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoDeputyPersonId(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        UserDTO deputyPerson = officeDTO.getDeputyPerson();
        if ( deputyPerson == null ) {
            return null;
        }
        String id = deputyPerson.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected AreaDTO officeToAreaDTO(Office office) {
        if ( office == null ) {
            return null;
        }

        AreaDTO areaDTO = new AreaDTO();

        areaDTO.setId( office.getAreaId() );

        return areaDTO;
    }

    protected OfficeDTO officeToOfficeDTO(Office office) {
        if ( office == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( office.getParentId() );

        return officeDTO;
    }

    protected UserDTO officeToUserDTO(Office office) {
        if ( office == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( office.getCreateBy() );

        return userDTO;
    }

    protected UserDTO officeToUserDTO1(Office office) {
        if ( office == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( office.getUpdateBy() );

        return userDTO;
    }

    protected UserDTO officeToUserDTO2(Office office) {
        if ( office == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( office.getPrimaryPerson() );

        return userDTO;
    }

    protected UserDTO officeToUserDTO3(Office office) {
        if ( office == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( office.getDeputyPerson() );

        return userDTO;
    }
}
