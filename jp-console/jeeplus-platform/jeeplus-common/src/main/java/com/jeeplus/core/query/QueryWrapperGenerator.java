package com.jeeplus.core.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeplus.common.utils.RequestUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;


public class QueryWrapperGenerator {

    public static <T> QueryWrapper<T> buildQueryCondition(T searchObj, Class<T> entityClass) throws Exception{
        QueryWrapper<T> queryWrapper = new QueryWrapper<T> ();

        Field[] fields = entityClass.getDeclaredFields ();

        for (int i = 0; i < fields.length; i++) {
            // 这个是检查类中属性是否含有查询注解
            Field field = fields[i];
            if (field.isAnnotationPresent (Query.class)) {
                // 获取查询注解
                Query annotation = field.getAnnotation (Query.class);
                // 获取查询的字段，如果没设置就使用属性名
                String column = StrUtil.isNotBlank (annotation.tableColumn ()) ? annotation.tableColumn () : StrUtil.toUnderlineCase (field.getName ());
                String fieldName = StrUtil.isNotBlank (annotation.javaField ())? annotation.javaField () : field.getName ();
                // 属性可读且值不为空
                    if (PropertyUtils.getNestedProperty (searchObj, field.getName ()) != null || annotation.type ().equals (QueryType.BETWEEN)) {
                        Object value = PropertyUtils.getNestedProperty (searchObj, fieldName);
                        if (value != null && StrUtil.isNotBlank (value.toString ()) || annotation.type ().equals (QueryType.BETWEEN)) {
                            switch (annotation.type ()) {
                                case EQ:
                                    queryWrapper.eq (column, value);
                                    break;
                                case NE:
                                    queryWrapper.ne (column, value);
                                    break;
                                case GT:
                                    queryWrapper.gt (column, value);
                                    break;
                                case GE:
                                    queryWrapper.ge (column, value);
                                    break;
                                case LT:
                                    queryWrapper.lt (column, value);
                                    break;
                                case LE:
                                    queryWrapper.le (column, value);
                                    break;
                                case BETWEEN: {
                                    Object begin =  RequestUtils.getRequest ().getParameter (  "begin" + StrUtil.upperFirst (fieldName));
                                    Object end =  RequestUtils.getRequest ().getParameter (  "end" + StrUtil.upperFirst (fieldName));
                                    if (begin != null && StrUtil.isNotBlank (begin.toString ()) && end != null && StrUtil.isNotBlank (end.toString ())) {
                                        queryWrapper.between (column, begin, end);
                                    }
                                    break;
                                }
                                case NOTBETWEEN: {
                                    Object begin =  RequestUtils.getRequest ().getParameter (  "begin" + StrUtil.upperFirst (fieldName));
                                    Object end =  RequestUtils.getRequest ().getParameter (  "end" + StrUtil.upperFirst (fieldName));
                                    if (begin != null && StrUtil.isNotBlank (begin.toString ()) && end != null && StrUtil.isNotBlank (end.toString ())) {
                                        queryWrapper.notBetween (column, begin, end);
                                    }
                                    break;
                                }
                                case LIKE:
                                    queryWrapper.like ( column, value );
                                    break;
                                case NOTLIKE:
                                    queryWrapper.notLike ( column, value );
                                    break;
                                case LIKELEFT:
                                    queryWrapper.likeLeft ( column, value );
                                    break;
                                case LIKERIGHT:
                                    queryWrapper.likeRight ( column, value );
                                    break;

                                default:
                                    queryWrapper.like (column, value);

                            }
                        }
                    }
            }
        }

        return queryWrapper;
    }

}
