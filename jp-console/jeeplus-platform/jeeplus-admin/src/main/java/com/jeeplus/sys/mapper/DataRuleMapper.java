/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.DataRule;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 数据权限MAPPER接口
 * @author lgf
 * @version 2021-04-02
 */
public interface DataRuleMapper extends BaseMapper<DataRule> {

	void deleteRoleDataRule(String dataRuleId);

	@InterceptorIgnore
	List<DataRuleDTO> findByUserId(UserDTO userDTO);
}
