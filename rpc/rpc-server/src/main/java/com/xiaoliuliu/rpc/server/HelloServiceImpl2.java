package com.xiaoliuliu.rpc.server;


import com.xiaoliuliu.rpc.api.HelloRpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:52:00
 */
@Slf4j
public class HelloServiceImpl2 implements HelloRpcService {

    static {
        System.out.println("HelloServiceImp2被创建");
    }

    public String hello(String say) {
        return say+"调用了服务端2的接口";
    }
}
