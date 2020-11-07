package com.xiaoliuliu.six.finger.web.demo.mybatis.test;

import com.xiaoliuliu.six.finger.web.demo.mybatis.bean.User;
import com.xiaoliuliu.six.finger.web.demo.mybatis.dao.UserMapper;
import com.xiaoliuliu.six.finger.web.mybatis.session.SqlSession;
import com.xiaoliuliu.six.finger.web.mybatis.session.SqlSessionFactory;
import com.xiaoliuliu.six.finger.web.mybatis.session.SqlSessionFactoryBuilder;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 16:15
 */
public class TestMain {

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args)
    {

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.getUser("1");
        System.out.println("-----" + user);
        System.out.println("-----" + userMapper.getAll());

        userMapper.updateUser("1");
        System.out.println("----" + userMapper.getAll());
    }
}
