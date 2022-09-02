package com.ulearn.service.impl;

import com.ulearn.dao.UserDao;
import com.ulearn.dao.domain.User;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
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
        // Check if the user exists
        User user = userDao.getUserByUsername(form.getUsername());
        if (user != null) {
            throw new CommonRuntimeException(CommonOperationError.USER_EXIST);
        }

        user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getUsername());
        user.setCreateTime(new Date());

        // 获取用户key
        String key = String.valueOf(new Date().getTime() % 100000000);
        user.setKey(key);

        // 加密密码
        String password = CryptUtil.encrypt(key, form.getPassword());
        user.setPassword(password);

        Integer rows = userDao.addUser(user);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.USER_SIGNUP_FAILED);
        }
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
}