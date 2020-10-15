package com.xiaoliuliu.six.finger.web.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 17:55
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {
    String value() default "";
}
