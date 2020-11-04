package com.xiaoliuliu.rpc.core.remoting.transport.netty.client;


import com.xiaoliuliu.rpc.common.enums.CompressTypeEnum;
import com.xiaoliuliu.rpc.common.enums.SerializationTypeEnum;
import com.xiaoliuliu.rpc.common.extension.ExtensionLoader;
import com.xiaoliuliu.rpc.common.factory.SingletonFactory;
import com.xiaoliuliu.rpc.core.registry.ServiceDiscovery;
import com.xiaoliuliu.rpc.core.remoting.transport.ClientTransport;
import com.xiaoliuliu.rpc.core.remoting.transport.constants.RpcConstants;
import com.xiaoliuliu.rpc.core.remoting.transport.dto.RpcMessage;
import com.xiaoliuliu.rpc.core.remoting.transport.dto.RpcRequest;
import com.xiaoliuliu.rpc.core.remoting.transport.dto.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * transport rpcRequest based on netty.
 *
 * @author shuang.kou
 * @createTime 2020年05月29日 11:34:00
 */
@Slf4j
public class NettyClientTransport implements ClientTransport {
    private final ServiceDiscovery serviceDiscovery;
    private final UnprocessedRequests unprocessedRequests;
    private final ChannelProvider channelProvider;

    public NettyClientTransport() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }

    @Override
    public CompletableFuture<RpcResponse<Object>> sendRpcRequest(RpcRequest rpcRequest) {
        // build return value
        CompletableFuture<RpcResponse<Object>> resultFuture = new CompletableFuture<>();
        // build rpc service name by rpcRequest
        String rpcServiceName = rpcRequest.toRpcProperties().toRpcServiceName();
        // get server address
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcServiceName);
        // get  server address related channel
        Channel channel = channelProvider.get(inetSocketAddress);
        if (channel != null && channel.isActive()) {
            // put unprocessed request
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setData(rpcRequest);
            rpcMessage.setCodec(SerializationTypeEnum.KYRO.getCode());
            rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
            rpcMessage.setMessageType(RpcConstants.REQUEST_TYPE);
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("client send message: [{}]", rpcMessage);
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("Send failed:", future.cause());
                }
            });
        } else {
            throw new IllegalStateException();
        }

        return resultFuture;
    }

}


