package com.ryd.business.service.util;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.basecommon.util.CacheConstant;

import java.math.BigDecimal;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service.util
 * 创建人：songby
 * 创建时间：2016/4/27 17:00
 */
public class Utils {


    /**
     * 计算佣金
     * @param quotePrice
     * @param amount
     * @param type
     * @param commissionPercent //佣金比例
     * @param taxPercent //税比例
     * @param rsMoney
     * @param commissionFee
     * @param taxFee
     */
    public static void calculate(BigDecimal quotePrice, long amount, short type, String commissionPercent, String taxPercent,
                                  BigDecimal rsMoney, BigDecimal commissionFee, BigDecimal taxFee){

        BigDecimal volMoney = ArithUtil.multiply(quotePrice, new BigDecimal(amount));
        //计算佣金
        commissionFee = ArithUtil.multiply(rsMoney, new BigDecimal(commissionPercent));

        //买股票
        if (type == ApplicationConstants.STOCK_QUOTETYPE_BUY.shortValue()) {

            rsMoney = ArithUtil.add(volMoney, commissionFee);

        }else if (type == ApplicationConstants.STOCK_QUOTETYPE_SELL.shortValue()) {//卖股票
            //计算印花税
            taxFee =  ArithUtil.multiply(volMoney, new BigDecimal(taxPercent));

            rsMoney =  ArithUtil.subtract(volMoney, ArithUtil.add(commissionFee, taxFee));

        }else{}

    }

}
