/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.database.datamodel.domain.DataParam;
import com.jeeplus.database.datamodel.mapper.DataParamMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据模型参数Service
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Service
@Transactional
public class DataParamService extends ServiceImpl <DataParamMapper, DataParam> {

    public Map<String, String> getParamsForMap(String dataSetId) {
        Map<String, String> paramsMap = new HashMap<>();
        //获取参数列表
        List<DataParam> paramList = super.lambdaQuery ().eq ( DataParam::getDataSetId, dataSetId ).list ();
        if (paramList.size() > 0) {
            for (DataParam param : paramList) {
                paramsMap.put(param.getField(), param.getDefaultValue());
            }
        }
        return paramsMap;
    }

}
