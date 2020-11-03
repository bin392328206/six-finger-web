package com.xiaoliuliu.rpc.server;


import com.xiaoliuliu.rpc.api.HelloRpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:52:00
 */
@Slf4j
public class HelloServiceImpl implements HelloRpcService {

    static {
        System.out.println("HelloServiceImpl被创建");
    }

    public String hello(String say) {
        return say+"调用了服务端的接口";
    }
}
