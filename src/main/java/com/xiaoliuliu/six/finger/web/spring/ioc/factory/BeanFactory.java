package com.xiaoliuliu.six.finger.web.spring.ioc.factory;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/19 10:08
 * 这个接口也是Spring ioc的核心接口呢,总的来说，Siprng ioc的实现了 我们需要实现2种，一种是基于注解的实现，一种是基于xml的实现
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

    <T> T getBean(Class<T> requiredType) throws Exception;

}
