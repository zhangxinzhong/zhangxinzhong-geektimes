package org.geekbang.projects.repository;

import org.geekbang.projects.domain.User;

import java.util.List;

/**
 * 用户的仓储
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-01 10:50
 **/
public interface UserRepository {

    /**
     * 创建对象
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean create(User user);

    /**
     * 获取所有用户
     * @return 成功返回 <code>List</code> 失败返回<code>null</code>
     */
    List<User> queryAll();
}
