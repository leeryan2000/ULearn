package com.ulearn.dao;

import com.ulearn.dao.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/30 22:27
 */

@Mapper
public interface UserDao {

    Integer addUser(User user);

    User getUserByUsername(@Param("username") String username);

    User getUserById(@Param("userId") Long userId);
}
