/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by 刘高峰 on 2018/3/18.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileData {
    private String id;
    private String value;
    private boolean open;
    private String type;
    private Long date;
    private String icon;
    private String size;
    private String pId;
    private List <FileData> data = Lists.newArrayList ( );
}
