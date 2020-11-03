package com.xiaoliuliu.rpc.core.remoting.transport.handler;


import com.xiaoliuliu.rpc.common.exception.RpcException;
import com.xiaoliuliu.rpc.common.factory.SingletonFactory;
import com.xiaoliuliu.rpc.core.remoting.transport.dto.RpcRequest;
import com.xiaoliuliu.rpc.core.remoting.transport.provider.ServiceProvider;
import com.xiaoliuliu.rpc.core.remoting.transport.provider.ServiceProviderImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RpcRequest processor
 *
 * @author shuang.kou
 * @createTime 2020年05月13日 09:05:00
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }

    /**
     * Processing rpcRequest: call the corresponding method, and then return the method
     */
    public Object handle(RpcRequest rpcRequest) {
        //
        Object service = serviceProvider.getService(rpcRequest.toRpcProperties());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * get method execution results
     *
     * @param rpcRequest client request
     * @param service    service object
     * @return the result of the target method execution
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            //通过反射执行调用service方法
            result = method.invoke(service, rpcRequest.getParameters());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
