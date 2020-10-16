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
        // 用于获取？号后面的参数
        Map<String, String> queryParameterMappings = UrlUtil.getQueryParams(requestUri);
        // 获取请求的path
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // 根据我们的请求路径，找到具体的方法，里面有封装路径参数和json参数
        MethodDetail methodDetail = DispatcherMethodMapper.getMethodDetail(requestPath, HttpMethod.GET);
        if (methodDetail == null) {
            return null;
        }
        //封装请求参数
        methodDetail.setQueryParameterMappings(queryParameterMappings);
        Method targetMethod = methodDetail.getMethod();
        if (targetMethod == null) {
            return null;
        }
        System.out.println("requestPath -> target method [{}]"+targetMethod.getName());
        //通过反射 拿到方法的参数
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod method
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            //根据参数上的注解，找到对应填充的参数和参数值填充的对象
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            if (parameterResolver != null) {
                //填充值的方法
                Object param = parameterResolver.resolve(methodDetail, parameter);
                //
                targetMethodParams.add(param);
            }
        }

        //因为我们现在还没有组合Spring 所以这个地方我们先自己通过反射来生成目标对象
        Object targetObject = ReflectionUtil.newInstance(methodDetail.getMethod().getDeclaringClass());
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, targetMethodParams.toArray());
    }
}
