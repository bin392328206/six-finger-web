package com.xiaoliuliu.six.finger.web.webmvc.hander;

import com.xiaoliuliu.six.finger.web.common.util.ReflectionUtil;
import com.xiaoliuliu.six.finger.web.common.util.UrlUtil;
import com.xiaoliuliu.six.finger.web.webmvc.entity.MethodDetail;
import com.xiaoliuliu.six.finger.web.webmvc.factory.DispatcherMethodMapper;
import com.xiaoliuliu.six.finger.web.webmvc.factory.ParameterResolverFactory;
import com.xiaoliuliu.six.finger.web.webmvc.resolver.ParameterResolver;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/15 9:34
 */
public class GetRequestHandler implements RequestHandler {
    @Override
    public Object handle(FullHttpRequest fullHttpRequest) throws Exception {
        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParameterMappings = UrlUtil.getQueryParams(requestUri);
        // 获取请求的path
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // 根据我们的请求路径，找到具体的方法
        MethodDetail methodDetail = DispatcherMethodMapper.getMethodDetail(requestPath, HttpMethod.GET);
        if (methodDetail == null) {
            return null;
        }
        methodDetail.setQueryParameterMappings(queryParameterMappings);
        Method targetMethod = methodDetail.getMethod();
        if (targetMethod == null) {
            return null;
        }
        System.out.println("requestPath -> target method [{}]"+targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod method
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            if (parameterResolver != null) {
                Object param = parameterResolver.resolve(methodDetail, parameter);
                targetMethodParams.add(param);
            }
        }

        //因为我们现在还没有组合Snprig 所以这个地方我们先自己通过反射来生成目标对象
        Object targetObject = ReflectionUtil.newInstance(methodDetail.getMethod().getDeclaringClass());
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, targetMethodParams.toArray());
    }
}
