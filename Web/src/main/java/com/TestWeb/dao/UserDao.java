package com.TestWeb.dao;

import com.TestWeb.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wbq on 2017/6/19.
 */
@Repository
public interface UserDao {

    User selectUserById(@Param("userId") Long userId);

    List<User> selectAllUser();
}
