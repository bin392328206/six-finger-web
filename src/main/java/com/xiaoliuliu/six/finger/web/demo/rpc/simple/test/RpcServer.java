package com.xiaoliuliu.six.finger.web.demo.rpc.simple.test;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/31 10:26
 */
public class RpcServer {

    //发布服务
    public static void main(String[] args) {
        RpcProxyServer server = new RpcProxyServer();
        server.publisherServer(8000);
    }
}
