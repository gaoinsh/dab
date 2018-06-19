/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package com.gaoinsh.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang.gao
 * @version $Id: BeanUtil.java, v 0.1 2018-06-19 17:36 xiang.gao Exp $$
 */
public class BeanUtil {

    public static <T> T beanCopy(Object fromBean, Class<T> type) {
        T toBean = null;
        try {
            toBean = type.newInstance();
            if (fromBean == null || toBean == null) {
                return null;
            }
            BeanUtils.copyProperties(fromBean, toBean);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return toBean;
    }

    public static <T> List<T> beanCopyList(List fromList, Class<T> type) {
        List<T> resultList = new ArrayList<>();
        for (int i = 0; fromList != null && i < fromList.size(); i++) {
            resultList.add(beanCopy(fromList.get(i), type));
        }
        return resultList;
    }

}