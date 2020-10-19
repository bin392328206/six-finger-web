package com.xiaoliuliu.six.finger.web.demo.servicec.impl;

import com.xiaoliuliu.six.finger.web.demo.servicec.UserService;
import com.xiaoliuliu.six.finger.web.spring.ioc.annotation.Component;
import com.xiaoliuliu.six.finger.web.spring.ioc.annotation.Service;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/19 12:23
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getUserName(String name) {
        return name+"小六六完成了ioc";
    }
}
