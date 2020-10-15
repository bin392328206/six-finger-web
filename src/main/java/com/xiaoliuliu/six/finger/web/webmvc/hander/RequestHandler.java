package com.xiaoliuliu.six.finger.web.webmvc.hander;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/14 16:18
 *  处理从Netty出来的通用处理接口
 */
public interface RequestHandler {
    Object handle(FullHttpRequest fullHttpRequest) throws Exception;
}
