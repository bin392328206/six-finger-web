package com.xiaoliuliu.six.finger.web.webmvc.annotation;

import com.xiaoliuliu.six.finger.web.webmvc.entity.RequestMethod;

import java.lang.annotation.*;

/**
 * @author 小六六
 * @date 2020/10/12 15:51
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";

    RequestMethod[] method() default {};
}
