package com.xiaoliuliu.six.finger.web.demo.rpc.simple.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/31 10:24
 * RPC 客户端代理实现
 */
public class RpcProxyClient<T> {
    public static Object proxyClient(Class clazz) {

      return   Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

              try (Socket socket = new Socket("localhost", 8000)) {
                  try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                      oos.writeUTF(method.getName());
                      oos.writeObject(args);
                      oos.flush();

                      try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                          return ois.readObject();
                      }
                  }
              }
          }
        });
    }

}
