package com.ulearn.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/8/30 22:29
 */

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Date create_time;
}
