package com.ulearn.dao;

import com.ulearn.dao.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/30 22:27
 */

@Mapper
public interface UserDao {

    void addUser(User user);
}
