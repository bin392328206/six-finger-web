package com.xiaoliuliu.six.finger.web.mybatis.executor;

import com.xiaoliuliu.six.finger.web.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:29
 */
public interface Executor {
    /**
     * 查询数据库
     *
     * @param ms
     * @param parameter
     * @return
     * @see
     */
    <E> List<E> doQuery(MappedStatement ms, Object parameter);

    /**
     * 更新操作
     *
     * @param ms
     * @param parameter
     */
    void doUpdate(MappedStatement ms, Object parameter);
}
