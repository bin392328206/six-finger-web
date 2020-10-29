package com.xiaoliuliu.six.finger.web.spring.ioc.content.support;

import com.xiaoliuliu.six.finger.web.spring.aop.AopProxy;
import com.xiaoliuliu.six.finger.web.spring.aop.CglibAopProxy;
import com.xiaoliuliu.six.finger.web.spring.aop.JdkDynamicAopProxy;
import com.xiaoliuliu.six.finger.web.spring.aop.config.AopConfig;
import com.xiaoliuliu.six.finger.web.spring.aop.support.AdvisedSupport;
import com.xiaoliuliu.six.finger.web.spring.ioc.annotation.Autowired;
import com.xiaoliuliu.six.finger.web.spring.ioc.beans.BeanDefinition;
import com.xiaoliuliu.six.finger.web.spring.ioc.beans.BeanWrapper;
import com.xiaoliuliu.six.finger.web.spring.ioc.beans.support.BeanDefinitionReader;
import com.xiaoliuliu.six.finger.web.spring.ioc.beans.support.XmlBeanDefinitionReader;
import com.xiaoliuliu.six.finger.web.spring.ioc.io.ClassPathResource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/19 10:34
 * ApplicationContext实现类中最重要的就是 refresh() 方法，它的流程就包括了IOC容器初始化、依赖注入和AOP
 */
public  class DefaultApplicationContext implements ApplicationContext {


    /**保存factoryBean和BeanDefinition的对应关系*/
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**保存了真正实例化的对象*/
    public static Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    /**
     *   配置文件的路径
     * @param name
     * @return
     * @throws Exception
     */
    private  String configLocation;


    private BeanDefinitionReader reader;

    /**
     *  构造方法
     * @param configLocation
     */
    public DefaultApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 核心方法
     */
    private void refresh() throws Exception {

        //1、定位，定位配置文件
        reader = new BeanDefinitionReader(this.configLocation);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //加载xml的bean,这边的xml只是简单做了一点点，比如他的很多的标签没有解析，只能说是一个半成品
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions1 = xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("test.xml"));
        beanDefinitions.addAll(beanDefinitions1);

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);

        //到这里为止，容器初始化完毕

        //4、把不是延时加载的类，提前初始化
        doAutowired();

    }

    private void doAutowired() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()){
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The \"" + beanDefinition.getFactoryBeanName() + "\" is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    @Override
    public  Object getBean(String beanName) throws Exception {
        //如果是单例，那么在上一次调用getBean获取该bean时已经初始化过了，拿到不为空的实例直接返回即可
        Object instance = getSingleton(beanName);
        if (instance != null) {
            return instance;
        }
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        //调用反射初始化Bean
        instance = instantiateBean(beanName, beanDefinition);


        //2.把这个对象封装到BeanWrapper中
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        //3.把BeanWrapper保存到IOC容器中去
        //注册一个类名（首字母小写，如helloService）
        factoryBeanInstanceCache.put(beanName, beanWrapper);

        //注册一个全类名（如com.lqb.HelloService）
       factoryBeanInstanceCache.put(beanDefinition.getBeanClassName(), beanWrapper);

        //4.注入 属性
        populateBean(beanName, new BeanDefinition(), beanWrapper);

        return factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    /**
     *  属性的注入
     * @param beanName
     * @param beanDefinition
     * @param beanWrapper
     */
    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper beanWrapper) {
        Class<?> wrappedClass = beanWrapper.getWrappedClass();
        for (Field declaredField : wrappedClass.getDeclaredFields()) {
            //如果没有被Autowired注解的成员变量则直接跳过
            if(!declaredField.isAnnotationPresent(Autowired.class)){
                continue;
            }

            Autowired autowired = declaredField.getAnnotation(Autowired.class);

            //拿到需要注入的类名
            String autowiredBeanName = autowired.value().trim();

            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = declaredField.getType().getName();
            }

            //强制访问该成员变量
            declaredField.setAccessible(true);

            if (factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                continue;
            }

            try {
                declaredField.set(beanWrapper.getWrappedInstance(), factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }

    }

    private Object instantiateBean(String beanName, BeanDefinition beanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = beanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
            Class<?> clazz = Class.forName(className);
            instance = clazz.newInstance();

            //############填充如下代码###############
            AdvisedSupport config = getAopConfig();
            //判断当前对象是否需要生成代理对象
            config.setTargetClass(clazz);
            config.setTarget(instance);

            //符合PointCut的规则的话，将创建代理对象
            if(config.pointCutMatch()) {
                instance = createProxy(config).getProxy();
            }
            //#############填充完毕##############

        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    /**
     *  @author: 小六六
     *  @Date: 2020/10/12 11:00
     *  @Description: 此方式是通过配置文件来封装config 还有也可以通过注解
     */
    private AdvisedSupport getAopConfig() {
        AopConfig config = new AopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new AdvisedSupport(config);
    }

    /**
     *  @author: 小六六
     *  @Date: 2020/10/12 11:02
     *  @Description: 有2种生成代理的方式，一个是jdk 一个是cglib
     */
    private AopProxy createProxy(AdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        //如果接口数量 > 0则使用JDK原生动态代理
        if(targetClass.getInterfaces().length > 0){
            return new JdkDynamicAopProxy(config);
        }
        CglibAopProxy cglibAopProxy = new CglibAopProxy(config);
        return cglibAopProxy;
    }





    private Object getSingleton(String beanName) {
        BeanWrapper beanWrapper = factoryBeanInstanceCache.get(beanName);
        return beanWrapper == null ? null : beanWrapper.getWrappedInstance();
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws Exception {
        return  (T) getBean(requiredType.getName());
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
