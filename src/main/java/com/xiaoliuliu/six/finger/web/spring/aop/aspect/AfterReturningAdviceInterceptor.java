package com.xiaoliuliu.six.finger.web.spring.aop.aspect;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/26 17:40
 */

import com.xiaoliuliu.six.finger.web.spring.aop.intercept.MethodInterceptor;
import com.xiaoliuliu.six.finger.web.spring.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 后置通知
 */
public class AfterReturningAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private JoinPoint joinPoint;

    public AfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        //先调用下一个拦截器
        Object retVal = mi.proceed();
        //再调用后置通知
        this.joinPoint = mi;
        this.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint, retVal, null);
    }
}
