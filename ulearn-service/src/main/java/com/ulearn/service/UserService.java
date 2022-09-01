package com.ulearn.service;

import com.ulearn.dao.domain.User;
import com.ulearn.dao.form.UserSignUpForm;
import org.springframework.stereotype.Service;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/31 14:16
 */

public interface UserService {

    void addUser(UserSignUpForm form);
}
