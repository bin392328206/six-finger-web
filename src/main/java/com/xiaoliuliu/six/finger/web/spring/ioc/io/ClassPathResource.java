package com.xiaoliuliu.six.finger.web.spring.ioc.io;

import com.xiaoliuliu.six.finger.web.spring.ioc.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/21 15:32
 */
public class ClassPathResource implements Resource {

    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }
    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream(this.path);

        if (is == null) {
            throw new FileNotFoundException(path + " cannot be opened");
        }
        return is;

    }
    @Override
    public String getDescription(){
        return this.path;
    }

}
