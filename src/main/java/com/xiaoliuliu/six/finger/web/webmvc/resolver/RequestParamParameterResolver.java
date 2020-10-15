package com.xiaoliuliu.six.finger.web.webmvc.resolver;

import com.xiaoliuliu.six.finger.web.common.util.ObjectUtil;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RequestParam;
import com.xiaoliuliu.six.finger.web.webmvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 17:57
 * 解析 @RequestParam 尽量还原SpringMVC
 */
public class RequestParamParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        if (requestParameterValue == null) {
            throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
        }
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }
}
