package com.xiaoliuliu.six.finger.web.spring.aop;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/26 17:41
 */
public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}

