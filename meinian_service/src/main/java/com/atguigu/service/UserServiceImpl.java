package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.UserDao;
import com.atguigu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements  UserService {
    @Autowired
    UserDao userDao;
    public User queryUserByUserName(String username) {


        return userDao.queryUserByUserName(username);
    }
}
