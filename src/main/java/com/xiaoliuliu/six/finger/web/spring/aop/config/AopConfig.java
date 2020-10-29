package com.xiaoliuliu.six.finger.web.spring.aop.config;

import lombok.Data;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/26 17:43
 */
@Data
public class AopConfig {

    //切点表达式
    private String pointCut;

    //前置通知方法
    private String aspectBefore;

    //后置通知方法
    private String aspectAfter;

    //切面类
    private String aspectClass;

    //异常通知方法
    private String aspectAfterThrow;

    //抛出的异常类型
    private String aspectAfterThrowingName;

}