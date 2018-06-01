/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package com.gaoinsh.service;

import com.gaoinsh.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiang.gao
 * @version $Id: UserService.java, v 0.1 2018-06-01 16:44 xiang.gao Exp $$
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void findByName(String name) {
        userDao.findByName(name);
    }
}