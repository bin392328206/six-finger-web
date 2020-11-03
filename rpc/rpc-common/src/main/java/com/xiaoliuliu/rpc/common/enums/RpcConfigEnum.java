package com.xiaoliuliu.rpc.common.enums;

/**
 *  @author: linliangkun
 *  @Date: 2020/11/2 15:40
 *  @Description:  这个是用来定义我们如何从配置文件中读取我们zk服务端的接口参数用的
 */
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;


    RpcConfigEnum(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

}
