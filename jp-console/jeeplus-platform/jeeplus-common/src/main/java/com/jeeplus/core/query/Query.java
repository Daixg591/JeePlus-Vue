package com.jeeplus.core.query;

import java.lang.annotation.*;

/**
 * 查询字段注解
 */
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
    /**
     * java属性名
     * @return
     */
    String javaField() default "";

    /**
     * mapper中的数据库字段名
     * @return
     */
    String tableColumn() default "";

    /**
     * 查询类型
     * @return
     */
    QueryType type() default QueryType.LIKE;

}
