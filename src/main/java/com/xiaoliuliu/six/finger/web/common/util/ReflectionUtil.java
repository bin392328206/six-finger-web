package com.xiaoliuliu.six.finger.web.common.util;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 18:05
 */
public class ReflectionUtil {

    /**
     * scan the classes marked by the specified annotation in the specified package
     *
     * @param packageName specified package name
     * @param annotation  specified annotation
     * @return the classes marked by the specified annotation in the specified package
     */
    public static Set<Class<?>> scanAnnotatedClass(String packageName, Class<? extends Annotation> annotation) {
        //这边实现是根据人家的api实现的，如果自己要实现，那么就要递龟遍历文件，然后判断每一个.class结尾的文件，再找到那个注解
        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        return annotatedClass;
    }

    /**
     * Get the implementation class of the interface
     *
     * @param packageName    specified package name
     * @param interfaceClass specified interface
     */
    public static <T> Set<Class<? extends T>> getSubClass(String packageName, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(interfaceClass);

    }

    /**
     * create object instance through class
     *
     * @param cls target class
     * @return object created by the target class
     */
    public static Object newInstance(Class<?> cls) {
        Object instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
        }
        return instance;
    }

    /**
     * set the value of a field in the object
     *
     * @param obj   target object
     * @param field target field
     * @param value the value assigned to the field
     */
    public static void setField(Object obj, Field field, Object value) {

        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * execute the target method
     *
     * @param method target method
     * @param args   method parameters
     * @return the result of method execution
     */
    public static Object executeTargetMethod(Object targetObject, Method method, Object... args) throws Exception {
        Object result;
        try {
            // invoke target method through reflection
            result = method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Exception(e.toString());
        }
        return result;
    }

}
