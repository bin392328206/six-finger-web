package com.xiaoliuliu.six.finger.web.spring.ioc.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface Service {
    String value() default "";
}
