package com.ulearn.service;

import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2023/3/11 2:01
 */


public interface MessageService {

    HashMap getInboxMessageByUserId(Long userId);
}
