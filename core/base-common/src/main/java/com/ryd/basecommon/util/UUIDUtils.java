package com.ryd.basecommon.util;

import java.util.UUID;
/**
 * <p>标题:UUIDUtils</p>
 * <p>描述:UUIDUtils</p>
 * 包名：com.ryd.basecommon.util
 * 创建人：songby
 * 创建时间：2016/4/20 17:22
 */
public class UUIDUtils {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    // 去掉"-"符号
    public static String uuidTrimLine() {
         String s = UUID.randomUUID().toString();
         return s.replace("-", "");
    }
}
