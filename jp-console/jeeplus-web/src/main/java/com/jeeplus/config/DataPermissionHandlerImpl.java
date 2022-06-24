package com.jeeplus.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import com.jeeplus.sys.utils.UserUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 自定义数据权限
 *
 * @Date 2021-05-7
 */
public class DataPermissionHandlerImpl implements DataPermissionHandler {

    private final Logger logger = LoggerFactory.getLogger(DataPermissionHandlerImpl.class);

    /**
     * 数据范围过滤
     * @param where 当前过滤的实体类
     */
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        if(UserUtils.getCurrentUserDTO () == null ||  StrUtil.isBlank(UserUtils.getCurrentUserDTO ().getId())){
            return where;
        }
        List<DataRuleDTO> dataRuleList = UserUtils.getDataRuleList();

        // 不存在数据规则，则不过滤数据
        if (UserUtils.getCurrentUserDTO ().getIsAdmin () || dataRuleList.size() == 0) {
            return where;
        }

        // 数据范围
        Expression expression = null;
        for(DataRuleDTO dataRule : dataRuleList){
            if(mappedStatementId.equals(dataRule.getClassName())){
                Expression condExpression = null;
                try {
                    condExpression = CCJSqlParserUtil.parseCondExpression(dataRule.getDataScopeSql());
                    expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, condExpression) : condExpression;
                } catch (JSQLParserException e) {
                    e.printStackTrace ();
                    logger.error("{}", e);
                }
            }

        }
        if(ObjectUtils.isEmpty (expression)) {
            return where;
        }else{
            return ObjectUtils.isNotEmpty(where) ? new AndExpression (where, new Parenthesis (expression)) : expression;
        }
    }


    /**
     * 构建过滤条件
     *
     * @param user 当前登录用户
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
//    public static Expression dataScopeFilter(SysUser user, String tableAlias, Expression where) {
//        Expression expression = null;
//        for (SysRole role : user.getRoles()) {
//            String dataScope = role.getDataScope();
//            if (DataScopeAspect.DATA_SCOPE_ALL.equals(dataScope)) {
//                return where;
//            }
//            if (DataScopeAspect.DATA_SCOPE_CUSTOM.equals(dataScope)) {
//                InExpression inExpression = new InExpression();
//                inExpression.setLeftExpression(buildColumn(tableAlias, "dept_id"));
//                SubSelect subSelect = new SubSelect();
//                PlainSelect select = new PlainSelect();
//                select.setSelectItems(Collections.singletonList(new SelectExpressionItem(new Column("dept_id"))));
//                select.setFromItem(new Table("sys_role_dept"));
//                EqualsTo equalsTo = new EqualsTo();
//                equalsTo.setLeftExpression(new Column ("role_id"));
//                equalsTo.setRightExpression(new LongValue(role.getRoleId()));
//                select.setWhere(equalsTo);
//                subSelect.setSelectBody(select);
//                inExpression.setRightExpression(subSelect);
//                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, inExpression) : inExpression;
//            }
//            if (DataScopeAspect.DATA_SCOPE_DEPT.equals(dataScope)) {
//                EqualsTo equalsTo = new EqualsTo();
//                equalsTo.setLeftExpression(buildColumn(tableAlias, "dept_id"));
//                equalsTo.setRightExpression(new LongValue(user.getDeptId()));
//                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression (expression, equalsTo) : equalsTo;
//            }
//            if (DataScopeAspect.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
//                InExpression inExpression = new InExpression();
//                inExpression.setLeftExpression(buildColumn(tableAlias, "dept_id"));
//                SubSelect subSelect = new SubSelect();
//                PlainSelect select = new PlainSelect();
//                select.setSelectItems(Collections.singletonList(new SelectExpressionItem (new Column("dept_id"))));
//                select.setFromItem(new Table ("sys_dept"));
//                EqualsTo equalsTo = new EqualsTo();
//                equalsTo.setLeftExpression(new Column("dept_id"));
//                equalsTo.setRightExpression(new LongValue(user.getDeptId()));
//                Function function = new Function();
//                function.setName("find_in_set");
//                function.setParameters(new ExpressionList (new LongValue (user.getDeptId()) , new Column("ancestors")));
//                select.setWhere(new OrExpression(equalsTo, function));
//                subSelect.setSelectBody(select);
//                inExpression.setRightExpression(subSelect);
//                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, inExpression) : inExpression;
//            }
//            if (DataScopeAspect.DATA_SCOPE_SELF.equals(dataScope)) {
//                EqualsTo equalsTo = new EqualsTo();
//                equalsTo.setLeftExpression(buildColumn(tableAlias, "create_by"));
//                equalsTo.setRightExpression(new StringValue (user.getUserName()));
//                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, equalsTo) : equalsTo;
//            }
//        }
//        return ObjectUtils.isNotEmpty(where) ? new AndExpression (where, new Parenthesis (expression)) : expression;
//    }
//
//    /**
//     * 构建Column
//     *
//     * @param tableAlias 表别名
//     * @param columnName 字段名称
//     * @return 带表别名字段
//     */
//    public static Column buildColumn(String tableAlias, String columnName) {
//        if (StringUtils.isNotEmpty(tableAlias)) {
//            columnName = tableAlias + "." + columnName;
//        }
//        return new Column(columnName);
//    }
}
