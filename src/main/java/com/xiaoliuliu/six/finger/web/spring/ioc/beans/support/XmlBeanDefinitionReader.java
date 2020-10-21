package com.xiaoliuliu.six.finger.web.spring.ioc.beans.support;

import com.xiaoliuliu.six.finger.web.spring.ioc.beans.BeanDefinition;
import com.xiaoliuliu.six.finger.web.spring.ioc.exception.BeanDefinitionStoreException;
import com.xiaoliuliu.six.finger.web.spring.ioc.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/10/21 15:11
 * 解析xml文件 把它变成数据结构
 */
public class XmlBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";


    public List<BeanDefinition> loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        List<BeanDefinition> result = new ArrayList<>();
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement(); //<beans>
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()) {
                Element ele = (Element) iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new BeanDefinition(beanClassName, id);
                result.add(bd);
            }
            return result;
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
