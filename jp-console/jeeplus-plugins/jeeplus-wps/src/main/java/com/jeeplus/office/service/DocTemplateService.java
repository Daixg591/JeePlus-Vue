/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.office.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.office.domain.DocTemplate;
import com.jeeplus.office.mapper.DocTemplateMapper;
import com.jeeplus.office.service.dto.DocTemplateDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 文档模板Service
 * @version 2021-06-23
 */
@Service
@Transactional(readOnly = true)
public class DocTemplateService extends ServiceImpl <DocTemplateMapper, DocTemplate> {

    public IPage <DocTemplateDTO> findPage(Page <DocTemplateDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper){
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
        return baseMapper.findList ( page, queryWrapper );
    }
}
