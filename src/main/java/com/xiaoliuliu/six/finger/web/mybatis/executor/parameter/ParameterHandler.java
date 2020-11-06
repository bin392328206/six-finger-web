package com.xiaoliuliu.six.finger.web.mybatis.executor.parameter;

import java.sql.PreparedStatement;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:34
 */
public interface ParameterHandler {
    /**
     * 设置参数
     *
     * @param paramPreparedStatement
     * @see
     */
    void setParameters(PreparedStatement paramPreparedStatement);
}
