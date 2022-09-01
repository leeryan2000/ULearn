package com.ulearn.service.impl;

import com.ulearn.dao.UserDao;
import com.ulearn.dao.domain.User;
import com.ulearn.dao.form.UserSignUpForm;
import com.ulearn.service.UserService;
import com.ulearn.service.util.CryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/31 14:16
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public void addUser(UserSignUpForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getUsername());
        // 加密密码
        String key = String.valueOf(new Date().getTime() % 100000000);
        String password = CryptUtil.encrypt(key, form.getPassword());
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setKey(key);
        userDao.addUser(user);
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
}
