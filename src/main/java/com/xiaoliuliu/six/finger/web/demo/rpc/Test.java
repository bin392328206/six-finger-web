package com.xiaoliuliu.six.finger.web.demo.rpc;

import java.util.ServiceLoader;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/2 10:17
 */
public class Test {
    public static void main(String[] args) {
        ServiceLoader<Car> serviceLoader = ServiceLoader.load(Car.class);
        for (Car car : serviceLoader) {
            System.out.println(car.getBrand());
        }
    }
}
