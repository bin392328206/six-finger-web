package com.xiaoliuliu.six.finger.web.mybatis.session;

import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 11:39
 */
public interface SqlSession {
    /**
     * 查询带条记录
     *
     * @param statementId
     * @param parameter
     * @return
     * @see
     */
    <T> T selectOne(String statementId, Object parameter);

    /**
     * 查询多条记录
     *
     * @param statementId
     * @param parameter
     * @return
     * @see
     */
    <E> List<E> selectList(String statementId, Object parameter);

    /**
     * update
     *
     * @param statementId
     * @param parameter
     */
    void update(String statementId, Object parameter);


    /**
     * insert
     *
     * @param statementId
     * @param parameter
     */
    void insert(String statementId, Object parameter);

    /**
     * 获取mapper
     *
     * @param paramClass
     * @return
     * @see
     */
    <T> T getMapper(Class<T> paramClass);

    /**
     * 获取配置类
     *
     * @return
     * @see
     */
    Configuration getConfiguration();
}
