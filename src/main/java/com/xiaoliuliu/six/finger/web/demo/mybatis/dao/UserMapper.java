package com.xiaoliuliu.six.finger.web.demo.mybatis.dao;

import com.xiaoliuliu.six.finger.web.demo.mybatis.bean.User;

import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 16:10
 */
public interface UserMapper {


    /**
     * 获取单个user
     *
     * @param id
     * @return
     * @see
     */
    User getUser(String id);

    /**
     * 获取所有用户
     *
     * @return
     * @see
     */
    List<User> getAll();

    /**
     * 更新用户（功能未完成）
     *
     * @param id
     */
    void updateUser(String id);
}
