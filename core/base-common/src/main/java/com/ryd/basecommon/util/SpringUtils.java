/**
 *
 * Copyright (c) 2013 by Tian Rui Si Chuang (BeiJing) Technology Co., Ltd
 * All rights reserved.
 *
 * The information contained herein is confidential and proprietary to
 * Tian Rui Si Chuang (BeiJing) Technology Co., Ltd, and considered a trade secret as defined under
 * civil and criminal statutes.  Tian Rui Si Chuang (BeiJing) Technology Co., Ltd shall pursue its
 * civil and criminal remedies in the event of unauthorized use or
 * misappropriation of its trade secrets.  Use of this information
 * by anyone other than authorized employees of Tian Rui Si Chuang (BeiJing) Technology Co., Ltd is
 * granted only under a written non-disclosure agreement, expressly
 * prescribing the scope and manner of such use.
 *
 */
package com.ryd.basecommon.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Springbean 处理工具
 */
@Service
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext app;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        app = applicationContext;
    }

    /**
     * 获取Spring applicationContext
     *
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return app;
    }

    /**
     * 根据类型获取BEAN
     *
     * @param cl
     * @param <T>
     *
     * @return
     */
    public static <T> T getBean(Class<T> cl) {
        return app.getBean(cl);
    }

    /**
     * 根据类型和名称获取BEAN
     * @param cl
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cl, String name) {
        return app.getBean(name, cl);
    }
}
