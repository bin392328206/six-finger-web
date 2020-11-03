package com.xiaoliuliu.rpc.client;

import com.xiaoliuliu.rpc.api.HelloRpcService;
import com.xiaoliuliu.rpc.common.entity.RpcServiceProperties;
import com.xiaoliuliu.rpc.core.proxy.RpcClientProxy;
import com.xiaoliuliu.rpc.core.remoting.transport.ClientTransport;
import com.xiaoliuliu.rpc.core.remoting.transport.socket.SocketRpcClient;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
public class RpcFrameworkSimpleClientMain {
    public static void main(String[] args) {
        ClientTransport clientTransport = new SocketRpcClient();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                .group("test2").version("version2").build();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(clientTransport, rpcServiceProperties);
        HelloRpcService helloRpcService = rpcClientProxy.getProxy(HelloRpcService.class);
        String hello = helloRpcService.hello("小六六");
        System.out.println(hello);
    }
}
