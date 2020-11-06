package com.xiaoliuliu.six.finger.web.mybatis.session.defaults;

import com.xiaoliuliu.six.finger.web.mybatis.constants.Constant;
import com.xiaoliuliu.six.finger.web.mybatis.session.Configuration;
import com.xiaoliuliu.six.finger.web.mybatis.session.SqlSession;
import com.xiaoliuliu.six.finger.web.mybatis.session.SqlSessionFactory;
import com.xiaoliuliu.six.finger.web.mybatis.utils.CommonUtis;
import com.xiaoliuliu.six.finger.web.mybatis.utils.XmlUtil;

import java.io.File;
import java.net.URL;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:22
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    /** the configuration */
    private final Configuration configuration;

    /**
     * @param configuration
     */
    public DefaultSqlSessionFactory(Configuration configuration)
    {

        this.configuration = configuration;
        loadMappersInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.", "/"));
    }

    /**
     * 开启会话
     *
     * @return
     */
    @Override
    public SqlSession openSession()
    {

        SqlSession session = new DefaultSqlSession(this.configuration);

        return session;
    }

    /**
     * loadMappersInfo
     *
     * @param dirName
     * @see
     */
    private void loadMappersInfo(String dirName)
    {

        URL resources = DefaultSqlSessionFactory.class.getClassLoader().getResource(dirName);

        File mappersDir = new File(resources.getFile());

        if (mappersDir.isDirectory())
        {

            // 显示包下所有文件
            File[] mappers = mappersDir.listFiles();
            if (CommonUtis.isNotEmpty(mappers))
            {
                for (File file : mappers)
                {

                    // 对文件夹继续递归
                    if (file.isDirectory())
                    {
                        loadMappersInfo(dirName + "/" + file.getName());

                    }
                    else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX))
                    {

                        // 只对XML文件解析
                        XmlUtil.readMapperXml(file, this.configuration);
                    }

                }

            }
        }

    }
}
