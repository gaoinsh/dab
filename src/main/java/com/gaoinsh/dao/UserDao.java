/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package com.gaoinsh.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author xiang.gao
 * @version $Id: UserDao.java, v 0.1 2018-06-01 16:46 xiang.gao Exp $$
 */
@Repository
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void findByName(String name) {
        logger.info("find db ,userName : " + name);
    }

}