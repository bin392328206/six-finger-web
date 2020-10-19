package com.xiaoliuliu.six.finger.web.spring.ioc.util;

import com.xiaoliuliu.six.finger.web.spring.ioc.annotation.Component;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/19 14:37
 */
public class IocUtil {
    public static String getBeanName(Class<?> aClass) {
        String beanName = aClass.getName();
        if (aClass.isAnnotationPresent(Component.class)) {
            Component component = aClass.getAnnotation(Component.class);
            beanName = "".equals(component.value()) ? aClass.getName() : component.value();
        }
        return beanName;
    }
}
