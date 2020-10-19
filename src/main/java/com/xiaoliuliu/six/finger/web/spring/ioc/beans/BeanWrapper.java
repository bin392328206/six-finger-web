package com.xiaoliuliu.six.finger.web.spring.ioc.beans;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020-10-11 11:18
 * 当BeanDefinition的Bean配置信息被读取并实例化成一个实例后，这个实例封装在BeanWrapper中。
 */
public class BeanWrapper {

    /**Bean的实例化对象*/
    private Object wrappedObject;

    public BeanWrapper(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public Object getWrappedInstance() {
        return this.wrappedObject;
    }

    public Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }
}

