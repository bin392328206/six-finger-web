package com.xiaoliuliu.six.finger.web.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/13 11:41
 * Netty 编写 HTTP 服务器
 * 主类
 */
public class HttpServer {

    /**
     * @Des 端口 http请求的端口
     * @Author 小六六
     * @Date 2020/10/13 11:42
     * @Param
     * @Return
     */
    int port;


    /**
     * @Des 构造方法
     * @Author 小六六
     * @Date 2020/10/13 11:42
     * @Param
     * @Return
     */
    public HttpServer(int port) {
        this.port = port;
    }

    /**
     * @Des 服务的启动方法
     * @Author 小六六
     * @Date 2020/10/13 11:43
     * @Param
     * @Return
     */
    public void start() throws Exception {
        //启动引导类
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        bootstrap.group(boss, work)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerInitializer());

        ChannelFuture cf = bootstrap.bind(new InetSocketAddress(port)).sync();
        System.out.println(" server start up on port : " + port);
        cf.channel().closeFuture().sync();
    }
}
