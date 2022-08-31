package com.ulearn.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/30 22:27
 */

@Mapper
public interface UserDao {

    void addUser();
}
