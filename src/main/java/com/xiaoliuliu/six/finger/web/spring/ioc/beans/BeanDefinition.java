package com.xiaoliuliu.six.finger.web.spring.ioc.beans;


import lombok.Data;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020-10-11 11:15
 * 我们原来使用xml作为配置文件时，定义的Bean其实在IOC容器中被封装成了BeanDefinition，也就是Bean的配置信息，包括className、是否为单例、是否需要懒加载等等
 */

@Data
public class BeanDefinition {

    /**
     *  类的类名
     */
    private String beanClassName;

    private boolean lazyInit = false;

    /**
     * 类的全限定名
     */
    private String factoryBeanName;

    public BeanDefinition() {}
}
