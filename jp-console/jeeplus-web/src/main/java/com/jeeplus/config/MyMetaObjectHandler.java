package com.jeeplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 填充器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "delFlag", Integer.class, 0); //新增数据时，默认为0  采用了注入器
        this.strictInsertFill(metaObject, "createBy", String.class, UserUtils.getCurrentUserDTO ().getId ());
        this.strictInsertFill(metaObject, "createDate", Date.class, new Date ());
        this.strictInsertFill(metaObject, "updateBy", String.class, UserUtils.getCurrentUserDTO ().getId ());
        this.strictInsertFill(metaObject, "updateDate", Date.class, new Date ());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateBy", String.class, UserUtils.getCurrentUserDTO ().getId ());
        this.strictUpdateFill(metaObject, "updateDate", Date.class, new Date ());
    }
}
