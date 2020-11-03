package com.xiaoliuliu.rpc.server;


import com.xiaoliuliu.rpc.api.HelloRpcService;
import com.xiaoliuliu.rpc.common.entity.RpcServiceProperties;
import com.xiaoliuliu.rpc.core.remoting.transport.socket.SocketRpcServer;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
public class RpcFrameworkSimpleServerMain {
    public static void main(String[] args) {
        HelloRpcService helloRpcService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                .group("test2").version("version2").build();
        socketRpcServer.registerService(helloRpcService, rpcServiceProperties);
        socketRpcServer.start();
    }
}
