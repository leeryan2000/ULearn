package com.ulearn.service;

import com.ulearn.dao.form.UserLoginForm;
import com.ulearn.dao.form.UserSignUpForm;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/31 14:16
 */

public interface UserService {

    void addUser(UserSignUpForm form);

    Long login(UserLoginForm form);
}
