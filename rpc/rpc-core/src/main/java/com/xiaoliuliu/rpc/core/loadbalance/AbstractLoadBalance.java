package com.xiaoliuliu.rpc.core.loadbalance;

import java.util.List;

/**
 * Abstract class for a load balancing policy
 *
 * 再抽象一层，因为无论你的实现类怎么样，只要是size等于1的处理方式都一样。
 *
 * @author shuang.kou
 * @createTime 2020年06月21日 07:44:00
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, String rpcServiceName) {
        if (serviceAddresses == null || serviceAddresses.size() == 0) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, rpcServiceName);
    }

    protected abstract String doSelect(List<String> serviceAddresses, String rpcServiceName);

}
