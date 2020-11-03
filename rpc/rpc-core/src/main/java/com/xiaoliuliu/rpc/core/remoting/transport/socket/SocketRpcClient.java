package com.xiaoliuliu.rpc.core.remoting.transport.socket;


import com.xiaoliuliu.rpc.common.entity.RpcServiceProperties;
import com.xiaoliuliu.rpc.common.exception.RpcException;
import com.xiaoliuliu.rpc.common.extension.ExtensionLoader;
import com.xiaoliuliu.rpc.core.registry.ServiceDiscovery;
import com.xiaoliuliu.rpc.core.remoting.transport.ClientTransport;
import com.xiaoliuliu.rpc.core.remoting.transport.dto.RpcRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 基于 Socket 传输 RpcRequest
 *
 * @author shuang.kou
 * @createTime 2020年05月10日 18:40:00
 *
 * 这个是基于传统IO的操作我们
 */
@AllArgsConstructor
@Slf4j
public class SocketRpcClient implements ClientTransport {
    // 这个是我们注册中心的组件
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        //通过spi 的机制加载我们的注册中心
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        // build rpc service name by rpcRequest
        String rpcServiceName = RpcServiceProperties.builder().serviceName(rpcRequest.getInterfaceName())
                .group(rpcRequest.getGroup()).version(rpcRequest.getVersion()).build().toRpcServiceName();
        //从注册中心中获取我们的网络ip
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcServiceName);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // Send data to the server through the output stream
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Read RpcResponse from the input stream
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}
