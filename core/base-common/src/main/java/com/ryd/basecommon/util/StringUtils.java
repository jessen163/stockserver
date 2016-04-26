package com.ryd.basecommon.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * 字符、对象基础工具类
 * Created by chenji on 2016/4/26.
 */
public class StringUtils {
    /**
     * 判空
     * TODO 待优化
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj==null) return true;
        if (obj instanceof Collection) {
            Collection c  = (Collection)obj;
            if (CollectionUtils.isEmpty(c)) {
                return true;
            }
        } else if (obj instanceof String) {
            String str = (String) obj;
            if (org.apache.commons.lang.StringUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }
}
