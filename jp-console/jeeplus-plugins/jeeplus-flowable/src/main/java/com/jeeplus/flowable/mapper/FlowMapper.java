/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.flowable.model.Flow;

/**
 * 审批Mapper接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface FlowMapper extends BaseMapper <Flow> {

    int updateProcInsIdByBusinessId(Flow act);

}
