package com.xiaoliuliu.six.finger.web.mybatis.session;

/**
 * @author 小六六
 * @version 1.0
 * @date 2020/11/6 11:26
 *  这个是什么？其实就是就是我们每次打开mysql连接
 */
public interface SqlSessionFactory {
    /**
     * 开启数据库会话
     *
     * @return
     * @see
     */
    SqlSession openSession();
}
