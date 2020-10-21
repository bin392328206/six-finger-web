package com.xiaoliuliu.six.finger.web.spring.ioc.exception;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/21 15:25
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);	}

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}