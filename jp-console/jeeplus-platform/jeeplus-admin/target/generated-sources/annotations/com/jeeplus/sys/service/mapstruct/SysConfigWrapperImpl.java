package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.SysConfig;
import com.jeeplus.sys.domain.vo.SysConfigVo;
import com.jeeplus.sys.service.dto.SysConfigDTO;
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
public class SysConfigWrapperImpl implements SysConfigWrapper {

    @Override
    public SysConfigDTO toDTO(SysConfig arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysConfigDTO sysConfigDTO = new SysConfigDTO();

        sysConfigDTO.setCreateBy( sysConfigToUserDTO( arg0 ) );
        sysConfigDTO.setUpdateBy( sysConfigToUserDTO1( arg0 ) );
        sysConfigDTO.setCreateDate( arg0.getCreateDate() );
        sysConfigDTO.setDelFlag( arg0.getDelFlag() );
        sysConfigDTO.setId( arg0.getId() );
        sysConfigDTO.setUpdateDate( arg0.getUpdateDate() );
        sysConfigDTO.setAccessKeyId( arg0.getAccessKeyId() );
        sysConfigDTO.setAccessKeySecret( arg0.getAccessKeySecret() );
        sysConfigDTO.setDefaultLayout( arg0.getDefaultLayout() );
        sysConfigDTO.setDefaultTheme( arg0.getDefaultTheme() );
        sysConfigDTO.setHomeUrl( arg0.getHomeUrl() );
        sysConfigDTO.setLogo( arg0.getLogo() );
        sysConfigDTO.setMailName( arg0.getMailName() );
        sysConfigDTO.setMailPassword( arg0.getMailPassword() );
        sysConfigDTO.setMultiAccountLogin( arg0.getMultiAccountLogin() );
        sysConfigDTO.setPort( arg0.getPort() );
        sysConfigDTO.setProductName( arg0.getProductName() );
        sysConfigDTO.setRemarks( arg0.getRemarks() );
        sysConfigDTO.setSignature( arg0.getSignature() );
        sysConfigDTO.setSingleLoginType( arg0.getSingleLoginType() );
        sysConfigDTO.setSmtp( arg0.getSmtp() );
        sysConfigDTO.setTemplateCode( arg0.getTemplateCode() );

        return sysConfigDTO;
    }

    @Override
    public List<SysConfigDTO> toDTO(List<SysConfig> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SysConfigDTO> list = new ArrayList<SysConfigDTO>( entityList.size() );
        for ( SysConfig sysConfig : entityList ) {
            list.add( toDTO( sysConfig ) );
        }

        return list;
    }

    @Override
    public Page<SysConfigDTO> toDTO(Page<SysConfig> page) {
        if ( page == null ) {
            return null;
        }

        Page<SysConfigDTO> page1 = new Page<SysConfigDTO>();

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
    public SysConfig toEntity(SysConfigDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SysConfig sysConfig = new SysConfig();

        sysConfig.setCreateBy( arg0CreateById( arg0 ) );
        sysConfig.setUpdateBy( arg0UpdateById( arg0 ) );
        sysConfig.setCreateDate( arg0.getCreateDate() );
        sysConfig.setDelFlag( arg0.getDelFlag() );
        sysConfig.setId( arg0.getId() );
        sysConfig.setUpdateDate( arg0.getUpdateDate() );
        sysConfig.setAccessKeyId( arg0.getAccessKeyId() );
        sysConfig.setAccessKeySecret( arg0.getAccessKeySecret() );
        sysConfig.setDefaultLayout( arg0.getDefaultLayout() );
        sysConfig.setDefaultTheme( arg0.getDefaultTheme() );
        sysConfig.setHomeUrl( arg0.getHomeUrl() );
        sysConfig.setLogo( arg0.getLogo() );
        sysConfig.setMailName( arg0.getMailName() );
        sysConfig.setMailPassword( arg0.getMailPassword() );
        sysConfig.setMultiAccountLogin( arg0.getMultiAccountLogin() );
        sysConfig.setPort( arg0.getPort() );
        sysConfig.setProductName( arg0.getProductName() );
        sysConfig.setRemarks( arg0.getRemarks() );
        sysConfig.setSignature( arg0.getSignature() );
        sysConfig.setSingleLoginType( arg0.getSingleLoginType() );
        sysConfig.setSmtp( arg0.getSmtp() );
        sysConfig.setTemplateCode( arg0.getTemplateCode() );

        return sysConfig;
    }

    @Override
    public List<SysConfig> toEntity(List<SysConfigDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SysConfig> list = new ArrayList<SysConfig>( dtoList.size() );
        for ( SysConfigDTO sysConfigDTO : dtoList ) {
            list.add( toEntity( sysConfigDTO ) );
        }

        return list;
    }

    @Override
    public Page<SysConfig> toEntity(Page<SysConfigDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<SysConfig> page1 = new Page<SysConfig>();

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
    public SysConfigVo toVo(SysConfig sysConfig) {
        if ( sysConfig == null ) {
            return null;
        }

        SysConfigVo sysConfigVo = new SysConfigVo();

        sysConfigVo.setDefaultLayout( sysConfig.getDefaultLayout() );
        sysConfigVo.setDefaultTheme( sysConfig.getDefaultTheme() );
        sysConfigVo.setLogo( sysConfig.getLogo() );
        sysConfigVo.setProductName( sysConfig.getProductName() );

        return sysConfigVo;
    }

    protected UserDTO sysConfigToUserDTO(SysConfig sysConfig) {
        if ( sysConfig == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( sysConfig.getCreateBy() );

        return userDTO;
    }

    protected UserDTO sysConfigToUserDTO1(SysConfig sysConfig) {
        if ( sysConfig == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( sysConfig.getUpdateBy() );

        return userDTO;
    }

    private String arg0CreateById(SysConfigDTO sysConfigDTO) {
        if ( sysConfigDTO == null ) {
            return null;
        }
        UserDTO createBy = sysConfigDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String arg0UpdateById(SysConfigDTO sysConfigDTO) {
        if ( sysConfigDTO == null ) {
            return null;
        }
        UserDTO updateBy = sysConfigDTO.getUpdateBy();
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
