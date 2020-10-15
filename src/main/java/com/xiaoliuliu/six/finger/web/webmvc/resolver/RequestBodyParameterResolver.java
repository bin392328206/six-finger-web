package com.xiaoliuliu.six.finger.web.webmvc.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RequestBody;
import com.xiaoliuliu.six.finger.web.webmvc.entity.MethodDetail;
import java.lang.reflect.Parameter;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 18:21
 * 解析 @RequestBody 尽量还原SpringMVC
 */
public class RequestBodyParameterResolver implements ParameterResolver {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        Object param = null;
        RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
        if (requestBody != null) {
            try {
                param = OBJECT_MAPPER.readValue(methodDetail.getJson(), parameter.getType());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }
}
