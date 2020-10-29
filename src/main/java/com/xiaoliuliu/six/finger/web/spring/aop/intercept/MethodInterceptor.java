package com.xiaoliuliu.six.finger.web.spring.aop.intercept;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/26 17:19
 */
public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;

}
