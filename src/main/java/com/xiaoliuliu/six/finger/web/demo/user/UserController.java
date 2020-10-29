package com.xiaoliuliu.six.finger.web.demo.user;

import com.xiaoliuliu.six.finger.web.demo.servicec.UserService;
import com.xiaoliuliu.six.finger.web.demo.servicec.impl.UserServiceImpl;
import com.xiaoliuliu.six.finger.web.spring.ioc.annotation.Autowired;
import com.xiaoliuliu.six.finger.web.spring.ioc.annotation.Component;
import com.xiaoliuliu.six.finger.web.spring.ioc.beans.BeanWrapper;
import com.xiaoliuliu.six.finger.web.spring.ioc.content.support.DefaultApplicationContext;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.GetMapping;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.PathVariable;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RequestMapping;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RestController;
import com.xiaoliuliu.six.finger.web.webmvc.entity.RequestMethod;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/15 10:49
 */
@RestController
@RequestMapping("/user")
@Component
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/{id}")
    public String get(@PathVariable("id") String id) {
        BeanWrapper xmlUserService = DefaultApplicationContext.factoryBeanInstanceCache.get("xmlUserService");
        Object wrappedInstance = xmlUserService.getWrappedInstance();
        System.out.println(wrappedInstance);
        return userServiceImpl.getUserName(id);
    }

    @RequestMapping(value = "/mapping/{id}",method = RequestMethod.GET)
    public Integer get0(@PathVariable("id") Integer id){
        return id;
    }
}
