package com.TestWeb.service.impl;

import com.TestWeb.dao.UserDao;
import com.TestWeb.model.User;
import com.TestWeb.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wbq on 2017/6/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{
    @Resource
    private UserDao userDao;
    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }
//
//    public User getUserByPhoneOrEmail(String emailOrPhone, Short state) {
//        return userDao.;
//    }

    public User getUserById(Long userId) {
        return userDao.selectUserById(userId);
    }
}
