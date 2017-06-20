package com.TestWeb.service;

import com.TestWeb.model.User;

import java.util.List;

/**
 * Created by wbq on 2017/6/19.
 */
public interface UserService {

    List<User> getAllUser();

//    User getUserByPhoneOrEmail(String emailOrPhone, Short state);

    User getUserById(Long userId);
}
