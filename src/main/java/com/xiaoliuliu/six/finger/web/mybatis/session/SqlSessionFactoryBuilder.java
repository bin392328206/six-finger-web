package com.xiaoliuliu.six.finger.web.mybatis.session;

import com.xiaoliuliu.six.finger.web.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:21
 */
public class SqlSessionFactoryBuilder {


    /**
     * build
     *
     * @param fileName
     * @return
     * @see
     */
    public SqlSessionFactory build(String fileName)
    {
        //1.定位，通过URL定位找到配置文件，然后转换为文件流
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(fileName.replace("classpath:", ""));
        //InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);

        return build(inputStream);
    }

    /**
     * build
     *
     * @param inputStream
     * @return
     * @see
     */
    public SqlSessionFactory build(InputStream inputStream)
    {
        try
        {
            Configuration.PROPS.load(inputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(new Configuration());
    }
}
