package com.xiaoliuliu.rpc.client;

import com.xiaoliuliu.rpc.api.HelloRpcService;
import com.xiaoliuliu.rpc.core.annotation.RpcReference;
import org.springframework.stereotype.Component;

/**
 * @author smile2coder
 */
@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloRpcService helloRpcService;

    public void test() throws InterruptedException {
        String hello = this.helloRpcService.hello("小六六");
        System.out.println(hello);
    }
}
