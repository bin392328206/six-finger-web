package com.xiaoliuliu.six.finger.web.demo.rpc.simple.test;

import lombok.Data;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/30 17:47
 * 远程服务接口实现类（Server）
 */
@Data
public class HelloService implements IHello {

    @Override
    public String sayHello(String info) {
        String result = "hello : " + info;
        System.out.println(result);
        return result;
    }
}