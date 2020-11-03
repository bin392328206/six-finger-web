package com.xiaoliuliu.rpc.core.registry.zk;


import com.xiaoliuliu.rpc.common.enums.RpcErrorMessageEnum;
import com.xiaoliuliu.rpc.common.exception.RpcException;
import com.xiaoliuliu.rpc.common.extension.ExtensionLoader;
import com.xiaoliuliu.rpc.core.loadbalance.LoadBalance;
import com.xiaoliuliu.rpc.core.registry.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * service discovery based on zookeeper
 *
 * @author shuang.kou
 * @createTime 2020年06月01日 15:16:00
 */
@Slf4j
public class ZkServiceDiscovery implements ServiceDiscovery {

    // 负载均衡组件
    private final LoadBalance loadBalance;

    public ZkServiceDiscovery() {
        //通过SPI 加载负载均衡组件
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList.size() == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcServiceName);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
