package com.xiaoliuliu.six.finger.web.webmvc.resolver;

import com.xiaoliuliu.six.finger.web.webmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 17:54
 */
public interface ParameterResolver {
    /**
     *  @author: linliangkun
     *  @Date: 2020/10/14 17:58
     *  @Description: 把从Http传进来的参数匹配到方法里面 （因为有不同的注解老来接收参数）
     */
    Object resolve(MethodDetail methodDetail, Parameter parameter);

}
