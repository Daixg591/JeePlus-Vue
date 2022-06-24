package com.jeeplus.flowable.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CollectionUtils {

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection 来源集合.
     * @param propertyName 要提取的属性名.
     */
    @SuppressWarnings("unchecked")
    public static List extractToList(final Collection collection, final String propertyName) {
        List list = new ArrayList (collection.size());

        try {
            for (Object obj : collection) {
                if(obj != null) {
                    list.add( PropertyUtils.getProperty(obj, propertyName));
                }
            }
        } catch (Exception e) {
            log.error ( "{}", e );
        }

        return list;
    }
}
