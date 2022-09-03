package com.ulearn.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ulearn.dao.UserDao;
import com.ulearn.dao.constant.UserConstant;
import com.ulearn.dao.domain.User;
import com.ulearn.dao.error.CommonOperationError;
import com.ulearn.dao.error.CommonRuntimeException;
import com.ulearn.dao.form.UserLoginForm;
import com.ulearn.dao.form.UserSignUpForm;
import com.ulearn.service.UserService;
import com.ulearn.service.util.CryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        user.setEmail(form.getUsername() + UserConstant.EMAIL_SUFFIX);
        user.setCreateTime(new Date());

        // 获取用户key(16 digits)
        String key = RandomUtil.randomString(16);
        user.setKey(key);

        // 加密密码
        String password = CryptUtil.encrypt(key, form.getPassword());
        user.setPassword(password);

        Integer rows = userDao.addUser(user);
        if (rows != 1) {
            throw new CommonRuntimeException(CommonOperationError.USER_SIGNUP_FAILED);
        }
    }

    /**
     * 用户登入, 成功登入返回用户ID
     * @param form 用户登入模板
     * @return 用户ID
     */
    @Override
    public Long login(UserLoginForm form) {
        User user = userDao.getUserByUsername(form.getUsername());
        // Check if the user exists
        if (user == null) {
            throw new CommonRuntimeException(CommonOperationError.USER_DOESNT_EXIST);
        }

        String password = CryptUtil.decrypt(user.getKey(), user.getPassword());
        if (!form.getPassword().equals(password)) {
            throw new CommonRuntimeException(CommonOperationError.USER_WRONG_PASSWORD);
        }

        return user.getId();
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
}
