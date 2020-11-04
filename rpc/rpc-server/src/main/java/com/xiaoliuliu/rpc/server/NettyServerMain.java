package com.xiaoliuliu.rpc.server;


import com.xiaoliuliu.rpc.common.entity.RpcServiceProperties;
import com.xiaoliuliu.rpc.core.annotation.RpcScan;
import com.xiaoliuliu.rpc.core.remoting.transport.netty.server.NettyServer;
import com.xiaoliuliu.six.finger.web.demo.rpc.simple.test.HelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Server: Automatic registration service via @RpcService annotation
 *
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
@RpcScan(basePackage = {"com.xiaoliuliu"})
public class NettyServerMain {
    public static void main(String[] args) {
        // Register service via annotation
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        nettyServer.start();
    }
}
