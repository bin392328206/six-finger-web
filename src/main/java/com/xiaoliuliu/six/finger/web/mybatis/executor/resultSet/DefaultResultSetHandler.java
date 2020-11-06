package com.xiaoliuliu.six.finger.web.mybatis.executor.resultSet;

import com.xiaoliuliu.six.finger.web.mybatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:40
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;

    /**
     * @param mappedStatement
     */
    public DefaultResultSetHandler(MappedStatement mappedStatement)
    {
        this.mappedStatement = mappedStatement;
    }

    /**
     * 处理查询结果，通过反射设置到返回的实体类
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet)
    {
        try
        {

            List<E> result = new ArrayList<>();

            if (null == resultSet)
            {
                return null;
            }

            while (resultSet.next())
            {
                // 通过反射实例化返回类
                Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                E entity = (E)entityClass.newInstance();
                Field[] declaredFields = entityClass.getDeclaredFields();

                for (Field field : declaredFields)
                {
                    // 对成员变量赋值
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    // 目前只实现了string和int转换
                    if (String.class.equals(fieldType))
                    {
                        field.set(entity, resultSet.getString(field.getName()));
                    }
                    else if (int.class.equals(fieldType) || Integer.class.equals(fieldType))
                    {
                        field.set(entity, resultSet.getInt(field.getName()));
                    }
                    else
                    {
                        // 其他类型自己转换，这里就直接设置了
                        field.set(entity, resultSet.getObject(field.getName()));
                    }
                }

                result.add(entity);
            }

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
