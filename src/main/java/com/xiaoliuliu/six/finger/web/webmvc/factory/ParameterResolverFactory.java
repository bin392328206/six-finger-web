package com.xiaoliuliu.six.finger.web.webmvc.factory;

import com.xiaoliuliu.six.finger.web.webmvc.annotation.PathVariable;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RequestBody;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RequestParam;
import com.xiaoliuliu.six.finger.web.webmvc.resolver.ParameterResolver;
import com.xiaoliuliu.six.finger.web.webmvc.resolver.PathVariableParameterResolver;
import com.xiaoliuliu.six.finger.web.webmvc.resolver.RequestBodyParameterResolver;
import com.xiaoliuliu.six.finger.web.webmvc.resolver.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 17:49
 * 参数解析器 类似于策略转发
 */
public class ParameterResolverFactory {

    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return new RequestBodyParameterResolver();
        }
        return null;
    }
}
