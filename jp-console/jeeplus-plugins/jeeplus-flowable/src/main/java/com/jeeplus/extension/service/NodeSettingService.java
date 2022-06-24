/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.NodeSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.extension.mapper.NodeSettingMapper;

/**
 * 节点设置Service
 *
 * @author 刘高峰
 * @version 2021-01-11
 */
@Service
@Transactional
public class NodeSettingService extends ServiceImpl <NodeSettingMapper, NodeSetting> {

    public NodeSetting queryByKey(String processDefId, String taskDefId, String key) {
        return lambdaQuery ().eq (  NodeSetting::getProcessDefId, processDefId).eq ( NodeSetting::getTaskDefId, taskDefId ).eq ( NodeSetting::getKey, key ).one ();
    }

    public void deleteByDefIdAndTaskId(NodeSetting nodeSetting) {
        lambdaUpdate ().eq ( NodeSetting::getProcessDefId, nodeSetting.getProcessDefId () ).eq ( NodeSetting::getTaskDefId, nodeSetting.getTaskDefId () ).remove ();
    }

    public void deleteByProcessDefId(String processDefId) {
        lambdaUpdate ().eq ( NodeSetting::getProcessDefId, processDefId ).remove ();
    }

}
