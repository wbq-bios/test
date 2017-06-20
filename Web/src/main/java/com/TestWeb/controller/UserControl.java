package com.TestWeb.controller;

import com.TestWeb.dao.UserDao;
import com.TestWeb.model.User;
import com.TestWeb.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wbq on 2017/6/19.
 */
@Controller
@RequestMapping("/user")
public class UserControl {
    private Logger log = Logger.getLogger(UserControl.class);
    @Resource
    private UserService userService;
    @RequestMapping("/showUser")
//    获取所有用户
    public String showAllUser(HttpServletRequest request, Model model){
        log.info("查询所有用户信息");
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        return "showUser";
    }

}
