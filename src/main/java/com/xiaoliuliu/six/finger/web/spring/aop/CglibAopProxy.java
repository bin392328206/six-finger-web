package com.xiaoliuliu.six.finger.web.spring.aop;

import com.xiaoliuliu.six.finger.web.spring.aop.intercept.MethodInvocation;
import com.xiaoliuliu.six.finger.web.spring.aop.support.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/12 11:03
 * cglib的动态代理
 */
public class CglibAopProxy implements AopProxy, MethodInterceptor {
    private AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        //cglib的方式
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.advised.getTargetClass());
        enhancer.setCallback(this);
        return  enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //获取拦截器链
        List<Object> interceptorsAndDynamicMethodMatchers =
                this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        //外层拦截器，用于控制拦截器链的执行
        MethodInvocation invocation = new MethodInvocation(
                proxy,
                this.advised.getTarget(),
                method,
                args,
                this.advised.getTargetClass(),
                interceptorsAndDynamicMethodMatchers
        );
        //开始连接器链的调用
        return invocation.proceed();
    }
}

