package com.xiaoliuliu.six.finger.web.demo.rpc.simple.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/30 17:47
 * 服务器代理实现
 */
public class RpcProxyServer {
    private IHello hello = new HelloService();

    public void publisherServer(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = ss.accept()) {
                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                        String method = ois.readUTF();
                        Object[] objs = (Object[]) ois.readObject();
                        Class<?>[] types = new Class[objs.length];
                        for (int i = 0; i < types.length; i++) {
                            types[i] = objs[i].getClass();
                        }
                        Method m = HelloService.class.getMethod(method, types);
                        Object obj = m.invoke(hello, objs);

                        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                            oos.writeObject(obj);
                            oos.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
