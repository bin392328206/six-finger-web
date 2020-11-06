package com.xiaoliuliu.six.finger.web.mybatis.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 15:25
 */
public class CommonUtis {
    /**
     * string is not empty
     *
     * @param src
     * @return
     */
    public static boolean isNotEmpty(String src)
    {
        return src != null && src.trim().length() > 0;
    }

    /**
     * list/set is not empty
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection)
    {
        return collection != null && !collection.isEmpty();
    }

    /**
     * map is not empty
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return map != null && !map.isEmpty();
    }

    /**
     * 数组不为空
     *
     * @param arr
     * @return
     * @see
     */
    public static boolean isNotEmpty(Object[] arr)
    {
        return arr != null && arr.length > 0;
    }

    /**
     * 对字符串去空白符和换行符等
     *
     * @return
     */
    public static String stringTrim(String src)
    {
        return (null != src) ? src.trim() : null;
    }
}
