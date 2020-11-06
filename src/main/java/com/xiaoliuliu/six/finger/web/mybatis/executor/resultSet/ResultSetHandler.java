package com.xiaoliuliu.six.finger.web.mybatis.executor.resultSet;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:39
 */
public interface ResultSetHandler {
    /**
     * 处理查询结果
     *
     * @param resultSet
     * @return
     * @see
     */
    <E> List<E> handleResultSets(ResultSet resultSet);

}
