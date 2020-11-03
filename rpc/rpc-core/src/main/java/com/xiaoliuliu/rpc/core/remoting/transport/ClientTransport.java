package com.xiaoliuliu.rpc.core.remoting.transport;

import com.xiaoliuliu.rpc.common.extension.SPI;
import com.xiaoliuliu.rpc.core.remoting.transport.dto.RpcRequest;

/**
 * @author shuang.kou
 * @version 1.0
 * @date 2020/11/2 15:08
 *这个怎么说呢？就是我们不是要远程调用嘛，那么远程调用的话，我们肯定是要定义发送请求的 和收到结果的接口，就是这个拉
 */
@SPI
public interface ClientTransport {

    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
