package com.xiaoliuliu.six.finger.web.server;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/15 15:35
 * 封装一个统一的返回，也就是我们servlet中 的Httpresponse
 */
public class WebResponse {
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");

    private static final JacksonTransform JACKSON_TRANSFORM;

    /**
     *  @author: linliangkun
     *  @Date: 2020/10/15 15:50
     *  @Description: 这个地方如果我们把Spring 写好了 就可以注入到容器中，这个我们可以后面再来重构
     */
    static {
        JACKSON_TRANSFORM = new JacksonTransform();
    }

    /**
     * @Des 成功的返回
     * @Author 小六六
     * @Date 2020/10/15 15:51
     * @Param
     * @Return
     */
    public static FullHttpResponse ok(Object o) {
        byte[] content = JACKSON_TRANSFORM.serialize(o);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    /**
     * @Des 失败的返回
     * @Author 小六六
     * @Date 2020/10/15 15:51
     * @Param
     * @Return
     */
    public static FullHttpResponse internalServerError(String url, String message) {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR.code(), INTERNAL_SERVER_ERROR.reasonPhrase(), message, url);
        byte[] content = JACKSON_TRANSFORM.serialize(errorResponse);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, INTERNAL_SERVER_ERROR, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}
