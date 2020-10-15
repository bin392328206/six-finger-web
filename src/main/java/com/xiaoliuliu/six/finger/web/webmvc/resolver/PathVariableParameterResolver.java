package com.xiaoliuliu.six.finger.web.webmvc.resolver;

import com.xiaoliuliu.six.finger.web.common.util.ObjectUtil;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.PathVariable;
import com.xiaoliuliu.six.finger.web.webmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 18:20
 * 解析 @PathVariable 尽量还原SpringMVC
 */
public class PathVariableParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        String requestParameter = pathVariable.value();
        Map<String, String> urlParameterMappings = methodDetail.getUrlParameterMappings();
        String requestParameterValue = urlParameterMappings.get(requestParameter);
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }
}

