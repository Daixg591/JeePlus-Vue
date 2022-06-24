package com.jeeplus.sys.domain.vo;

import lombok.Data;

@Data
public class SysConfigVo {
    /*
       外观配置
     */
    private String defaultTheme;//默认主题
    private String defaultLayout;
    private String productName;//产品名称
    private String logo;//产品logo;

}
