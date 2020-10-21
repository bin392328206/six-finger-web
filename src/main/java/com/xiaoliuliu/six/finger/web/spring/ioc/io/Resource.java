package com.xiaoliuliu.six.finger.web.spring.ioc.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/21 15:18
 */
public interface Resource {
    public InputStream getInputStream() throws IOException;
    public String getDescription();
}

