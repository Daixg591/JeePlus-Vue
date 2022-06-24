/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.common.redis.RedisUtils;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.domain.SysConfig;
import com.jeeplus.sys.mapper.SysConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置Service
 * @author 刘高峰
 * @version 2021-09-18
 */
@Service
@Transactional
public class SysConfigService extends ServiceImpl<SysConfigMapper, SysConfig> {

	public SysConfig getById(String id) {
		SysConfig sysConfig =(SysConfig) RedisUtils.getInstance().get ( CacheNames.SYS_CACHE_CONFIG, id);
		if(sysConfig == null){
			sysConfig = super.getById (id);
			RedisUtils.getInstance() .set ( CacheNames.SYS_CACHE_CONFIG, id, sysConfig);
		}
		return sysConfig;
	}

	public boolean updateById(SysConfig sysConfig) {
		RedisUtils.getInstance().delete ( CacheNames.SYS_CACHE_CONFIG, sysConfig.getId () );
		return super.updateById (sysConfig);
	}


}
