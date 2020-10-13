package com.xiaoliuliu.six.finger.web.demo.server;

import com.xiaoliuliu.six.finger.web.server.HttpServer;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/13 14:26
 *  这个类 用于 搭建Netty web服务器的测试类，只适用于搭建教程的第一篇文章
 */
public class ApplicationServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer(8081);// 8081为启动端口
        server.start();
    }
}