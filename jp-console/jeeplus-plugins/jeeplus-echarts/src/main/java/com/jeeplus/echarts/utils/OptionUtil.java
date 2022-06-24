package com.jeeplus.echarts.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.jeeplus.database.datamodel.service.DataSetService;
import com.jeeplus.echarts.domain.vo.ChartDataVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OptionUtil {

    public static ChartDataVO getChartData(String dataSetId, String xnames, String ynames, HttpServletRequest request) throws Exception {
        DataSetService dataSetService = SpringUtil.getBean(DataSetService.class);
        List<Map<String, Object>> list = dataSetService.queryForListById(dataSetId, request);
        ChartDataVO chartData = new ChartDataVO ();
        chartData.getColumns().addAll(Arrays.asList(xnames.split(",")));
        chartData.getColumns().addAll(Arrays.asList(ynames.split(",")));
        for (Map map : list) {
            Map nMap = new LinkedHashMap();
            for (String name : chartData.getColumns()) {
                nMap.put(name, map.get(name));
            }
            chartData.getRows().add(nMap);
        }

        return chartData;
    }

}
