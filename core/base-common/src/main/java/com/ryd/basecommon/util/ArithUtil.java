package com.ryd.basecommon.util;

import java.math.BigDecimal;

/**
 * <p>标题:精确度计算</p>
 * <p>描述:精确度计算</p>
 * 包名：com.ryd.basecommon.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class ArithUtil {

    private static final int DEF_DIV_SCALE=10;

    private ArithUtil(){}

    /**
     * 加 d1+d2
     * @param d1 参数1
     * @param d2 参数2
     * @return
     */
    public static double add(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }


    public static BigDecimal add(BigDecimal b1,BigDecimal b2){
        return b1.add(b2);
    }

    /**
     * 减 d1-d2
     * @param d1 参数1
     * @param d2 参数2
     * @return
     */
    public static double subtract(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    public static BigDecimal subtract(BigDecimal b1,BigDecimal b2){
        return b1.subtract(b2);
    }

    /**
     * 乘 d1*d2
     * @param d1 参数1
     * @param d2 参数2
     * @return
     */
    public static double multiply(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();

    }

    public static BigDecimal multiply(BigDecimal b1,BigDecimal b2){
        return b1.multiply(b2);
    }

    /**
     * 除 d1/d2
     * @param d1 参数1
     * @param d2 参数2
     * @return
     */
    public static double divide(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return div(b1, b2, DEF_DIV_SCALE).doubleValue();
    }

    public static BigDecimal divide(BigDecimal b1,BigDecimal b2){
        return div(b1, b2, DEF_DIV_SCALE);
    }

    private static BigDecimal div(BigDecimal b1,BigDecimal b2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);

    }

    /**
     * 比较
     * @param d1 参数1
     * @param d2 参数2
     * @return
     */
    public static int compare(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));

        return b1.compareTo(b2);
    }

    public static int compare(BigDecimal b1,BigDecimal b2){
        return b1.compareTo(b2);
    }

}
