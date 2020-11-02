package com.xiaoliuliu.six.finger.web.demo.rpc;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/2 10:17
 */
public class Benz implements Car {
    @Override
    public String getBrand() {
        System.out.println("benz car");
        return "Benz";
    }
}