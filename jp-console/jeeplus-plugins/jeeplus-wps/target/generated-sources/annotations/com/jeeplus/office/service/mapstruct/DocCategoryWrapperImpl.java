package com.jeeplus.office.service.mapstruct;

import com.jeeplus.office.domain.DocCategory;
import com.jeeplus.office.service.dto.DocCategoryDTO;
import com.jeeplus.office.service.dto.DocTemplateDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T11:53:14+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210708-0430, environment: Java 17 (Eclipse Adoptium)"
)
@Component
public class DocCategoryWrapperImpl implements DocCategoryWrapper {

    @Override
    public DocCategoryDTO copyDTO(DocCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DocCategoryDTO docCategoryDTO = new DocCategoryDTO();

        docCategoryDTO.setCreateBy( dto.getCreateBy() );
        docCategoryDTO.setCreateDate( dto.getCreateDate() );
        docCategoryDTO.setDelFlag( dto.getDelFlag() );
        docCategoryDTO.setId( dto.getId() );
        docCategoryDTO.setUpdateBy( dto.getUpdateBy() );
        docCategoryDTO.setUpdateDate( dto.getUpdateDate() );
        List<DocCategoryDTO> list = dto.getChildren();
        if ( list != null ) {
            docCategoryDTO.setChildren( new ArrayList<DocCategoryDTO>( list ) );
        }
        docCategoryDTO.setName( dto.getName() );
        docCategoryDTO.setParent( copyDTO( dto.getParent() ) );
        docCategoryDTO.setParentIds( dto.getParentIds() );
        docCategoryDTO.setSort( dto.getSort() );
        List<DocTemplateDTO> list1 = dto.getDocTemplateDTOList();
        if ( list1 != null ) {
            docCategoryDTO.setDocTemplateDTOList( new ArrayList<DocTemplateDTO>( list1 ) );
        }
        docCategoryDTO.setRemarks( dto.getRemarks() );

        return docCategoryDTO;
    }

    @Override
    public DocCategory copyEntity(DocCategory entity) {
        if ( entity == null ) {
            return null;
        }

        DocCategory docCategory = new DocCategory();

        docCategory.setCreateBy( entity.getCreateBy() );
        docCategory.setCreateDate( entity.getCreateDate() );
        docCategory.setDelFlag( entity.getDelFlag() );
        docCategory.setId( entity.getId() );
        docCategory.setUpdateBy( entity.getUpdateBy() );
        docCategory.setUpdateDate( entity.getUpdateDate() );
        List<DocCategory> list = entity.getChildren();
        if ( list != null ) {
            docCategory.setChildren( new ArrayList<DocCategory>( list ) );
        }
        docCategory.setName( entity.getName() );
        docCategory.setParentId( entity.getParentId() );
        docCategory.setParentIds( entity.getParentIds() );
        docCategory.setSort( entity.getSort() );
        docCategory.setRemarks( entity.getRemarks() );

        return docCategory;
    }

    @Override
    public DocCategoryDTO toDTO(DocCategory arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DocCategoryDTO docCategoryDTO = new DocCategoryDTO();

        docCategoryDTO.setParent( docCategoryToDocCategoryDTO( arg0 ) );
        docCategoryDTO.setCreateBy( docCategoryToUserDTO( arg0 ) );
        docCategoryDTO.setUpdateBy( docCategoryToUserDTO1( arg0 ) );
        docCategoryDTO.setCreateDate( arg0.getCreateDate() );
        docCategoryDTO.setDelFlag( arg0.getDelFlag() );
        docCategoryDTO.setId( arg0.getId() );
        docCategoryDTO.setUpdateDate( arg0.getUpdateDate() );
        docCategoryDTO.setChildren( toDTO( arg0.getChildren() ) );
        docCategoryDTO.setName( arg0.getName() );
        docCategoryDTO.setParentIds( arg0.getParentIds() );
        docCategoryDTO.setSort( arg0.getSort() );
        docCategoryDTO.setRemarks( arg0.getRemarks() );

        return docCategoryDTO;
    }

    @Override
    public List<DocCategoryDTO> toDTO(List<DocCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DocCategoryDTO> list = new ArrayList<DocCategoryDTO>( entityList.size() );
        for ( DocCategory docCategory : entityList ) {
            list.add( toDTO( docCategory ) );
        }

        return list;
    }

    @Override
    public DocCategory toEntity(DocCategoryDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DocCategory docCategory = new DocCategory();

        docCategory.setParentId( arg0ParentId( arg0 ) );
        docCategory.setCreateBy( arg0CreateById( arg0 ) );
        docCategory.setUpdateBy( arg0UpdateById( arg0 ) );
        docCategory.setCreateDate( arg0.getCreateDate() );
        docCategory.setDelFlag( arg0.getDelFlag() );
        docCategory.setId( arg0.getId() );
        docCategory.setUpdateDate( arg0.getUpdateDate() );
        docCategory.setChildren( toEntity( arg0.getChildren() ) );
        docCategory.setName( arg0.getName() );
        docCategory.setParentIds( arg0.getParentIds() );
        docCategory.setSort( arg0.getSort() );
        docCategory.setRemarks( arg0.getRemarks() );

        return docCategory;
    }

    @Override
    public List<DocCategory> toEntity(List<DocCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DocCategory> list = new ArrayList<DocCategory>( dtoList.size() );
        for ( DocCategoryDTO docCategoryDTO : dtoList ) {
            list.add( toEntity( docCategoryDTO ) );
        }

        return list;
    }

    protected DocCategoryDTO docCategoryToDocCategoryDTO(DocCategory docCategory) {
        if ( docCategory == null ) {
            return null;
        }

        DocCategoryDTO docCategoryDTO = new DocCategoryDTO();

        docCategoryDTO.setId( docCategory.getParentId() );

        return docCategoryDTO;
    }

    protected UserDTO docCategoryToUserDTO(DocCategory docCategory) {
        if ( docCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( docCategory.getCreateBy() );

        return userDTO;
    }

    protected UserDTO docCategoryToUserDTO1(DocCategory docCategory) {
        if ( docCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( docCategory.getUpdateBy() );

        return userDTO;
    }

    private String arg0ParentId(DocCategoryDTO docCategoryDTO) {
        if ( docCategoryDTO == null ) {
            return null;
        }
        DocCategoryDTO parent = docCategoryDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0CreateById(DocCategoryDTO docCategoryDTO) {
        if ( docCategoryDTO == null ) {
            return null;
        }
        UserDTO createBy = docCategoryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(DocCategoryDTO docCategoryDTO) {
        if ( docCategoryDTO == null ) {
            return null;
        }
        UserDTO updateBy = docCategoryDTO.getUpdateBy();
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
