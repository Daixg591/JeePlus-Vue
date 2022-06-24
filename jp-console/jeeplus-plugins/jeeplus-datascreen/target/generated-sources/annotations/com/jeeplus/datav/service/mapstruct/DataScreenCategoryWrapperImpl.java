package com.jeeplus.datav.service.mapstruct;

import com.jeeplus.datav.domain.DataScreenCategory;
import com.jeeplus.datav.service.dto.DataScreenCategoryDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:52:45+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class DataScreenCategoryWrapperImpl implements DataScreenCategoryWrapper {

    @Override
    public DataScreenCategoryDTO copyDTO(DataScreenCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataScreenCategoryDTO dataScreenCategoryDTO = new DataScreenCategoryDTO();

        dataScreenCategoryDTO.setCreateBy( dto.getCreateBy() );
        dataScreenCategoryDTO.setCreateDate( dto.getCreateDate() );
        dataScreenCategoryDTO.setDelFlag( dto.getDelFlag() );
        dataScreenCategoryDTO.setId( dto.getId() );
        dataScreenCategoryDTO.setUpdateBy( dto.getUpdateBy() );
        dataScreenCategoryDTO.setUpdateDate( dto.getUpdateDate() );
        List<DataScreenCategoryDTO> list = dto.getChildren();
        if ( list != null ) {
            dataScreenCategoryDTO.setChildren( new ArrayList<DataScreenCategoryDTO>( list ) );
        }
        dataScreenCategoryDTO.setParent( copyDTO( dto.getParent() ) );
        dataScreenCategoryDTO.setParentIds( dto.getParentIds() );
        dataScreenCategoryDTO.setSort( dto.getSort() );
        dataScreenCategoryDTO.setName( dto.getName() );
        dataScreenCategoryDTO.setRemarks( dto.getRemarks() );

        return dataScreenCategoryDTO;
    }

    @Override
    public DataScreenCategory copyEntity(DataScreenCategory entity) {
        if ( entity == null ) {
            return null;
        }

        DataScreenCategory dataScreenCategory = new DataScreenCategory();

        dataScreenCategory.setCreateBy( entity.getCreateBy() );
        dataScreenCategory.setCreateDate( entity.getCreateDate() );
        dataScreenCategory.setDelFlag( entity.getDelFlag() );
        dataScreenCategory.setId( entity.getId() );
        dataScreenCategory.setUpdateBy( entity.getUpdateBy() );
        dataScreenCategory.setUpdateDate( entity.getUpdateDate() );
        List<DataScreenCategory> list = entity.getChildren();
        if ( list != null ) {
            dataScreenCategory.setChildren( new ArrayList<DataScreenCategory>( list ) );
        }
        dataScreenCategory.setName( entity.getName() );
        dataScreenCategory.setParentId( entity.getParentId() );
        dataScreenCategory.setParentIds( entity.getParentIds() );
        dataScreenCategory.setSort( entity.getSort() );
        dataScreenCategory.setRemarks( entity.getRemarks() );

        return dataScreenCategory;
    }

    @Override
    public DataScreenCategoryDTO toDTO(DataScreenCategory arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DataScreenCategoryDTO dataScreenCategoryDTO = new DataScreenCategoryDTO();

        dataScreenCategoryDTO.setParent( dataScreenCategoryToDataScreenCategoryDTO( arg0 ) );
        dataScreenCategoryDTO.setCreateBy( dataScreenCategoryToUserDTO( arg0 ) );
        dataScreenCategoryDTO.setUpdateBy( dataScreenCategoryToUserDTO1( arg0 ) );
        dataScreenCategoryDTO.setCreateDate( arg0.getCreateDate() );
        dataScreenCategoryDTO.setDelFlag( arg0.getDelFlag() );
        dataScreenCategoryDTO.setId( arg0.getId() );
        dataScreenCategoryDTO.setUpdateDate( arg0.getUpdateDate() );
        dataScreenCategoryDTO.setChildren( toDTO( arg0.getChildren() ) );
        dataScreenCategoryDTO.setParentIds( arg0.getParentIds() );
        dataScreenCategoryDTO.setSort( arg0.getSort() );
        dataScreenCategoryDTO.setName( arg0.getName() );
        dataScreenCategoryDTO.setRemarks( arg0.getRemarks() );

        return dataScreenCategoryDTO;
    }

    @Override
    public List<DataScreenCategoryDTO> toDTO(List<DataScreenCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataScreenCategoryDTO> list = new ArrayList<DataScreenCategoryDTO>( entityList.size() );
        for ( DataScreenCategory dataScreenCategory : entityList ) {
            list.add( toDTO( dataScreenCategory ) );
        }

        return list;
    }

    @Override
    public DataScreenCategory toEntity(DataScreenCategoryDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DataScreenCategory dataScreenCategory = new DataScreenCategory();

        dataScreenCategory.setParentId( arg0ParentId( arg0 ) );
        dataScreenCategory.setCreateBy( arg0CreateById( arg0 ) );
        dataScreenCategory.setUpdateBy( arg0UpdateById( arg0 ) );
        dataScreenCategory.setCreateDate( arg0.getCreateDate() );
        dataScreenCategory.setDelFlag( arg0.getDelFlag() );
        dataScreenCategory.setId( arg0.getId() );
        dataScreenCategory.setUpdateDate( arg0.getUpdateDate() );
        dataScreenCategory.setChildren( toEntity( arg0.getChildren() ) );
        dataScreenCategory.setName( arg0.getName() );
        dataScreenCategory.setParentIds( arg0.getParentIds() );
        dataScreenCategory.setSort( arg0.getSort() );
        dataScreenCategory.setRemarks( arg0.getRemarks() );

        return dataScreenCategory;
    }

    @Override
    public List<DataScreenCategory> toEntity(List<DataScreenCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataScreenCategory> list = new ArrayList<DataScreenCategory>( dtoList.size() );
        for ( DataScreenCategoryDTO dataScreenCategoryDTO : dtoList ) {
            list.add( toEntity( dataScreenCategoryDTO ) );
        }

        return list;
    }

    protected DataScreenCategoryDTO dataScreenCategoryToDataScreenCategoryDTO(DataScreenCategory dataScreenCategory) {
        if ( dataScreenCategory == null ) {
            return null;
        }

        DataScreenCategoryDTO dataScreenCategoryDTO = new DataScreenCategoryDTO();

        dataScreenCategoryDTO.setId( dataScreenCategory.getParentId() );

        return dataScreenCategoryDTO;
    }

    protected UserDTO dataScreenCategoryToUserDTO(DataScreenCategory dataScreenCategory) {
        if ( dataScreenCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataScreenCategory.getCreateBy() );

        return userDTO;
    }

    protected UserDTO dataScreenCategoryToUserDTO1(DataScreenCategory dataScreenCategory) {
        if ( dataScreenCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataScreenCategory.getUpdateBy() );

        return userDTO;
    }

    private String arg0ParentId(DataScreenCategoryDTO dataScreenCategoryDTO) {
        if ( dataScreenCategoryDTO == null ) {
            return null;
        }
        DataScreenCategoryDTO parent = dataScreenCategoryDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0CreateById(DataScreenCategoryDTO dataScreenCategoryDTO) {
        if ( dataScreenCategoryDTO == null ) {
            return null;
        }
        UserDTO createBy = dataScreenCategoryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(DataScreenCategoryDTO dataScreenCategoryDTO) {
        if ( dataScreenCategoryDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataScreenCategoryDTO.getUpdateBy();
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
