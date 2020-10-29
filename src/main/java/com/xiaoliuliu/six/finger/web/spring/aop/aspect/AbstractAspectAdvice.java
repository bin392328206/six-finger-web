package com.xiaoliuliu.six.finger.web.spring.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/26 17:23
 */
public abstract class AbstractAspectAdvice implements Advice{
    /**通知方法*/
    private Method aspectMethod;

    /**切面类*/
    private Object aspectTarget;

    public AbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    /**
     * 调用通知方法
     */
    public Object invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable {
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if (null == paramTypes || paramTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                if (paramTypes[i] == JoinPoint.class) {
                    args[i] = joinPoint;
                } else if (paramTypes[i] == Throwable.class) {
                    args[i] = tx;
                } else if (paramTypes[i] == Object.class) {
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget, args);
        }
    }
}
