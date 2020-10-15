package com.xiaoliuliu.six.finger.web.server;

import com.xiaoliuliu.six.finger.web.common.util.UrlUtil;
import com.xiaoliuliu.six.finger.web.webmvc.factory.RequestHandlerFactory;
import com.xiaoliuliu.six.finger.web.webmvc.hander.RequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpUtil.is100ContinueExpected;


/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/13 12:01
 * 核心处理http请求的类，包括url的匹配核心方法都是在channelRead0方法
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        System.out.println("获得的参数:"+req);

        //100 Continue含义
        //HTTP客户端程序有一个实体的主体部分要发送给服务器，但希望在发送之前查看下服务器是否会接受这个实体，所以在发送实体之前先发送了一个携带100 Continue的Expect请求首部的请求。
        //服务器在收到这样的请求后，应该用 100 Continue或一条错误码来进行响应。
        if (is100ContinueExpected(req)) {
            ctx.write(new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.CONTINUE));
        }
        // 获取请求的uri
        String uri = req.uri();
        //获取uri, 过滤指定的资源
        if("/favicon.ico".equals(uri)) {
            System.out.println("请求了 favicon.ico, 不做响应");
            return;
        }
        //根据不同的url返回不同要处理的方法handler
        RequestHandler requestHandler = RequestHandlerFactory.create(req.method());

        Object result;
        FullHttpResponse response;
        try {
            //真正执行我们的业务代码
            result = requestHandler.handle(req);
            //成功的返回封装
            response = WebResponse.ok(result);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String requestPath = UrlUtil.getRequestPath(req.uri());
            response = WebResponse.internalServerError(requestPath, e.toString());
        }
        //判断连接是否关闭
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
