package org.geekbang.projects.service.impl;


import org.geekbang.projects.domain.User;
import org.geekbang.projects.repository.DataBaseUserRepository;
import org.geekbang.projects.service.UserService;

import java.util.List;

/**
 * 实现 {@link UserService}
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-02-28 20:00
 **/
public class UserServiceImpl implements UserService {

    private final DataBaseUserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new DataBaseUserRepository();
    }


    @Override
    public boolean register(User user) {
        return userRepository.create(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public List<User> queryAll() {
        return userRepository.queryAll();
    }
}
