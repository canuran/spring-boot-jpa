package ewing.application;

import com.querydsl.core.group.GroupExpression;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPQLQuery;
import ewing.application.paging.Pager;
import ewing.application.paging.Pages;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询帮助类。
 *
 * @author Ewing
 */
public class QueryHelper {

    private QueryHelper() {

    }

    /**
     * 使用分页参数和查询对象进行分页查询。
     */
    public static <T> Pages<T> queryPage(Pager pager, JPQLQuery<T> query) {
        if (pager == null) {
            query.limit(100).offset(0);
        } else {
            query.limit(pager.getLimit()).offset(pager.getPage() * pager.getLimit());
        }
        return new Pages<>(query.fetchCount(), query.fetch());
    }

    /**
     * 使用全部Expression（包括实体查询对象）参数查询指定类型的Bean。
     */
    public static <T> QBean<T> allToBean(
            Class<? extends T> type, Expression... expressions) {
        List<Expression> all = new ArrayList<>();
        for (Expression expression : expressions) {
            if (expression instanceof EntityPathBase) {
                try {
                    Field[] fields = expression.getClass().getFields();
                    for (Field field : fields) {
                        Object object = field.get(expression);
                        if (object instanceof SimpleExpression
                                && object instanceof Path
                                && !(object instanceof EntityPath)) {
                            all.add((Path) object);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                all.add(expression);
            }
        }
        return Projections.bean(type, all.toArray(new Expression[all.size()]));
    }

    /**
     * 使用与Bean属性匹配的Expression（包括实体查询对象）参数查询Bean。
     */
    public static <T> QBean<T> matchToBean(
            Class<? extends T> type, Expression... expressions) {
        // 获取到Bean的所有属性
        PropertyDescriptor[] properties;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            properties = beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        // 获取参数中能够用的上的表达式
        Map<String, Expression<?>> expressionMap = new HashMap<>();
        for (Expression expression : expressions) {
            if (expression instanceof EntityPathBase) {
                // 逐个匹配实体查询对象中的路径
                try {
                    Field[] fields = expression.getClass().getFields();
                    for (Field field : fields) {
                        Object object = field.get(expression);
                        if (object instanceof SimpleExpression
                                && object instanceof Path
                                && !(object instanceof EntityPath)) {
                            matchBindings(expressionMap, properties, (Path) object);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 匹配单个路径表达式是否用的上
                matchBindings(expressionMap, properties, expression);
            }
        }
        return Projections.bean(type, expressionMap);
    }

    /**
     * 根据属性匹配Expression并添加绑定到MapBuilder。
     * 实现逻辑参考自QBean.createBindings方法。
     */
    private static void matchBindings(
            Map<String, Expression<?>> expressionMap,
            PropertyDescriptor[] properties, Expression expression) {
        if (expression instanceof Path<?>) {
            String name = ((Path<?>) expression).getMetadata().getName();
            for (PropertyDescriptor property : properties) {
                if (property.getName().equals(name) && property.getWriteMethod() != null) {
                    expressionMap.put(name, expression);
                    break; // 匹配到属性结束内层循环
                }
            }
        } else if (expression instanceof Operation<?>) {
            Operation<?> operation = (Operation<?>) expression;
            if (operation.getOperator() == Ops.ALIAS
                    && operation.getArg(1) instanceof Path<?>) {
                String name = ((Path<?>) operation.getArg(1)).getMetadata().getName();
                for (PropertyDescriptor property : properties) {
                    if (property.getName().equals(name) && property.getWriteMethod() != null) {
                        Expression<?> express = operation.getArg(0);
                        if (express instanceof FactoryExpression
                                || express instanceof GroupExpression) {
                            expressionMap.put(name, express);
                        } else {
                            expressionMap.put(name, operation);
                        }
                        break; // 匹配到属性结束内层循环
                    }
                }
            } else {
                throw new IllegalArgumentException("Unsupported expression " + expression);
            }
        } else {
            throw new IllegalArgumentException("Unsupported expression " + expression);
        }
    }

}
