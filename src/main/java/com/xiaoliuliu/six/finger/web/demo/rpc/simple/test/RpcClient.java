package com.xiaoliuliu.six.finger.web.demo.rpc.simple.test;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/31 10:26
 */
public class RpcClient {

    // 调用服务
    public static void main(String[] args) {
        IHello hello = (IHello) RpcProxyClient.proxyClient(IHello.class);
        String s = hello.sayHello("小六六写rpc demo呀");
        System.out.println(s);

    }
}
