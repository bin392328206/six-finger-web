package com.xiaoliuliu.six.finger.web.spring.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/26 16:26
 *通知方法的其中一个入参就是<kbd>JoinPoint</kbd>，通过它我们可以拿到当前被代理的对象、方法、参数等，甚至可以设置一个参数用于上下文传递，从接口方法就能判断出来了。
 */
public interface JoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
