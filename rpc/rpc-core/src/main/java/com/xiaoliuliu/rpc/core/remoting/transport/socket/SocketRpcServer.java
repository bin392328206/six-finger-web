package com.xiaoliuliu.rpc.core.remoting.transport.socket;

import com.xiaoliuliu.rpc.common.entity.RpcServiceProperties;
import com.xiaoliuliu.rpc.common.factory.SingletonFactory;
import com.xiaoliuliu.rpc.common.utils.ThreadPoolFactoryUtils;
import com.xiaoliuliu.rpc.core.config.CustomShutdownHook;
import com.xiaoliuliu.rpc.core.remoting.transport.provider.ServiceProvider;
import com.xiaoliuliu.rpc.core.remoting.transport.provider.ServiceProviderImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


/**
 * @author shuang.kou
 * @createTime 2020年05月10日 08:01:00
 */
@Slf4j
public class SocketRpcServer {

    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;


    public SocketRpcServer() {
        threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        SingletonFactory.getInstance(ServiceProviderImpl.class);
        serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }


    public void registerService(Object service) {
        serviceProvider.publishService(service);
    }

    public void registerService(Object service, RpcServiceProperties rpcServiceProperties) {
        serviceProvider.publishService(service, rpcServiceProperties);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, 9998));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }

}
