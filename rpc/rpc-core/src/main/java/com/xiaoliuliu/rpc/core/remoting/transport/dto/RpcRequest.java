package com.xiaoliuliu.rpc.core.remoting.transport.dto;

import com.xiaoliuliu.rpc.common.entity.RpcServiceProperties;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.*;

import java.io.Serializable;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 08:24:00
 * 这个类是用来封装调用服务端的参数的
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    //这个方法是用来封装成一个key 就是我们客户端要从注册中心去找到这个key
    public RpcServiceProperties toRpcProperties() {
        return RpcServiceProperties.builder().serviceName(this.getInterfaceName())
                .version(this.getVersion())
                .group(this.getGroup()).build();
    }
}
