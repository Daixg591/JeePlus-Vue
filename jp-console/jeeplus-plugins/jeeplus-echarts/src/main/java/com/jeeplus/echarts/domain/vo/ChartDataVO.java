package com.jeeplus.echarts.domain.vo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChartDataVO {

    private List<String> columns = Lists.newArrayList();

    private List<Map> rows = Lists.newArrayList();
}
