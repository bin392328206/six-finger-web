package com.xiaoliuliu.six.finger.web.demo.rpc;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/2 10:16
 */
public class BM implements Car  {
    @Override
    public String getBrand() {
        System.out.println("BM car");
        return "BM";
    }
}