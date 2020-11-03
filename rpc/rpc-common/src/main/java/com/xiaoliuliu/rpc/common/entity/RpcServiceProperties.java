package com.xiaoliuliu.rpc.common.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.*;

/**
 * @author shuang.kou
 * @createTime 2020年07月21日 20:23:00
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RpcServiceProperties {
    /**
     * service version
     */
    private String version;
    /**
     * when the interface has multiple implementation classes, distinguish by group
     */
    private String group;
    private String serviceName;

    public String toRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }
}
