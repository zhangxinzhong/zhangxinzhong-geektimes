package org.geekbang.projects.service;


import org.geekbang.projects.domain.User;

import java.util.List;

/**
 * 用户服务
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-02-28 20:00
 **/
public interface UserService {

    /**
     * 注册用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean register(User user);

    /**
     * 注销用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean deregister(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return
     */
    boolean update(User user);

    /**
     * 通过 id 获取用户
     * @param id 主键id
     * @return
     */
    User queryUserById(Long id);

    /**
     * 通过用户名获取用户
     * @param name
     * @param password
     * @return
     */
    User queryUserByNameAndPassword(String name, String password);

    /**
     * 获取所有的用户
     * @return
     */
    List<User> queryAll();
}
