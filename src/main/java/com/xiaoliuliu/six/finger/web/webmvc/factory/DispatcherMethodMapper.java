package com.xiaoliuliu.six.finger.web.webmvc.factory;

import com.xiaoliuliu.six.finger.web.common.util.ReflectionUtil;
import com.xiaoliuliu.six.finger.web.common.util.UrlUtil;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.GetMapping;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.PostMapping;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RequestMapping;
import com.xiaoliuliu.six.finger.web.webmvc.annotation.RestController;
import com.xiaoliuliu.six.finger.web.webmvc.entity.MethodDetail;
import com.xiaoliuliu.six.finger.web.webmvc.entity.RequestMethod;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 17:36
 * <p>
 * 其实这个类呢？就是为不同的请求去构造对应的MethodDetail
 */
public class DispatcherMethodMapper {

    //配置文件
    private static Properties config = new Properties();

    //配置文件中指定需要扫描的包名
    private static final String SCAN_PACKAGE = "scanPackage";

    // get request url -> target method.
    // eg: "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> UserController.get(java.lang.Integer)
    //用来封装方法的返回值
    private static final Map<String, Method> GET_REQUEST_MAPPINGS = new HashMap<>();
    // post request url -> target method.
    private static final Map<String, Method> POST_REQUEST_MAPPINGS = new HashMap<>();
    // formatted get request url -> original url
    // eg : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> /user/{id}
    //封装参数
    private static final Map<String, String> GET_URL_MAPPINGS = new HashMap<>();
    // formatted post request url -> original url
    private static final Map<String, String> POST_URL_MAPPINGS = new HashMap<>();

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        if (httpMethod == HttpMethod.GET) {
            methodDetail.build(requestPath, GET_REQUEST_MAPPINGS, GET_URL_MAPPINGS);
            return methodDetail;
        }
        if (httpMethod == HttpMethod.POST) {
            methodDetail.build(requestPath, POST_REQUEST_MAPPINGS, POST_URL_MAPPINGS);
            return methodDetail;
        }
        return null;
    }

    public static void loadRoutes() {
        //从配置文件读取需要扫描的路径
        String packageName = config.getProperty(SCAN_PACKAGE);
        //首先扫描要扫描包下面的
        Set<Class<?>> restControllerSets = ReflectionUtil.scanAnnotatedClass(packageName, RestController.class);
        for (Class<?> aClass : restControllerSets) {
            //获取 RestController 注解
            RestController restController = aClass.getAnnotation(RestController.class);
            if (null != restController) {
                Method[] methods = aClass.getDeclaredMethods();
                // 获取RequestMapping注解
                RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
                String baseUrl = requestMapping.value();
                //遍历该类下面的所有方法
                for (Method method : methods) {
                    //判断方法上是否有http请求的注释
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMappingMethod = method.getAnnotation(RequestMapping.class);
                        if (null == requestMappingMethod.method()) {
                            //如果为null，默认设置为Get
                            String url = baseUrl + requestMappingMethod.value();
                            setContext(method, url, GET_REQUEST_MAPPINGS, GET_URL_MAPPINGS);
                        } else {
                            RequestMethod[] mappingMethod = requestMappingMethod.method();
                            for (RequestMethod requestMethod : mappingMethod) {
                                String url = baseUrl + requestMappingMethod.value();
                                if (requestMethod.equals(RequestMethod.GET)) {
                                    setContext(method, url, GET_REQUEST_MAPPINGS, GET_URL_MAPPINGS);
                                }
                                if (requestMethod.equals(RequestMethod.POST)) {
                                    setContext(method, url, POST_REQUEST_MAPPINGS, POST_URL_MAPPINGS);
                                }
                            }

                        }
                    }
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        if (getMapping != null) {
                            //获取url的前缀  RequestMapping的value值
                            String url = baseUrl + getMapping.value();
                            setContext(method, url, GET_REQUEST_MAPPINGS, GET_URL_MAPPINGS);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            String url = baseUrl + postMapping.value();
                            setContext(method, url, POST_REQUEST_MAPPINGS, POST_URL_MAPPINGS);
                        }
                    }
                }
            }
        }
    }

    /**
     * @author: linliangkun
     * @Date: 2020/10/15 16:32
     * @Description: 把方法和url对应封装到容器中
     */
    private static void setContext(Method method, String url, Map<String, Method> getRequestMappings, Map<String, String> getUrlMappings) {
        String formattedUrl = UrlUtil.formatUrl(url);
        getRequestMappings.put(formattedUrl, method);
        getUrlMappings.put(formattedUrl, url);
    }
}
