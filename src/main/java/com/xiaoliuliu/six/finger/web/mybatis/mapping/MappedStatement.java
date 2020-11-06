package com.xiaoliuliu.six.finger.web.mybatis.mapping;

import com.xiaoliuliu.six.finger.web.mybatis.constants.Constant;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 14:46
 */
public class MappedStatement {
    /** mapper文件的namespace */
    private String namespace;

    /** sql的id属性 */
    private String sqlId;

    /** sql语句，对应源码的sqlSource */
    private String sql;

    /** 返回类型 */
    private String resultType;

    /** sqlCommandType对应select/update/insert等 */
    private Constant.SqlType sqlCommandType;

    /**
     * @return the namespace
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * @param namespace
     *            the namespace to set
     */
    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * @return the sqlId
     */
    public String getSqlId()
    {
        return sqlId;
    }

    /**
     * @param sqlId
     *            the sqlId to set
     */
    public void setSqlId(String sqlId)
    {
        this.sqlId = sqlId;
    }

    /**
     * @return the sql
     */
    public String getSql()
    {
        return sql;
    }

    /**
     * @param sql
     *            the sql to set
     */
    public void setSql(String sql)
    {
        this.sql = sql;
    }

    /**
     * @return the resultType
     */
    public String getResultType()
    {
        return resultType;
    }

    /**
     * @param resultType
     *            the resultType to set
     */
    public void setResultType(String resultType)
    {
        this.resultType = resultType;
    }


    /**
     * @return Returns the sqlCommandType.
     */
    public Constant.SqlType getSqlCommandType()
    {
        return sqlCommandType;
    }

    /**
     * @param sqlCommandType The sqlCommandType to set.
     */
    public void setSqlCommandType(Constant.SqlType sqlCommandType)
    {
        this.sqlCommandType = sqlCommandType;
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "MappedStatement [namespace=" + namespace + ", sqlId=" + sqlId + ", sql=" + sql + ", resultType="
                + resultType + "]";
    }
}
