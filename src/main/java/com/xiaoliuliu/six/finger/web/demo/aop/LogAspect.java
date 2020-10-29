package com.xiaoliuliu.six.finger.web.demo.aop;


import com.xiaoliuliu.six.finger.web.spring.aop.aspect.JoinPoint;

import java.util.Arrays;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/12 11:33
 */
public class LogAspect {
    //在调用一个方法之前，执行before方法
    public void before(JoinPoint joinPoint){
        joinPoint.setUserAttribute("startTime_" + joinPoint.getMethod().getName(),System.currentTimeMillis());
        //这个方法中的逻辑，是由我们自己写的;
        System.out.println("动态代理前置通知");
    }

    //在调用一个方法之后，执行after方法
    public void after(JoinPoint joinPoint){
        System.out.println("动态代理后置通知");
        long startTime = (Long) joinPoint.getUserAttribute("startTime_" + joinPoint.getMethod().getName());
        long endTime = System.currentTimeMillis();
        System.out.println("use time :" + (endTime - startTime));
    }

    public void afterThrowing(JoinPoint joinPoint, Throwable ex){
        System.out.println(("出现异常" +
                "\nTargetObject:" +  joinPoint.getThis() +
                "\nArgs:" + Arrays.toString(joinPoint.getArguments()) +
                "\nThrows:" + ex.getMessage()));
    }
}
