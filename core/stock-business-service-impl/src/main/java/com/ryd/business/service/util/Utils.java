package com.ryd.business.service.util;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.model.StQuote;

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
     * @param commissionPercentStr //佣金比例
     * @param taxPercentStr //税比例
     * @param minCommissionFeeStr //最小佣金ee
     * @param minTaxFeeStr //最小税
     * @return rsArr[0] 佣金 rsArr[1] 资金数额 rsArr[2] 印花税
     */
    public static BigDecimal[] calculate(BigDecimal quotePrice, long amount, short type, String commissionPercentStr, String taxPercentStr, String minCommissionFeeStr, String minTaxFeeStr) {

        BigDecimal[] rsArr = new BigDecimal[3];
        BigDecimal minCommissionFee = formatStr2Decimal(minCommissionFeeStr);
        BigDecimal commissionPercent = formatStr2Decimal(commissionPercentStr);

        BigDecimal volMoney = ArithUtil.multiply(quotePrice, BigDecimal.valueOf(amount));
        //计算佣金
        BigDecimal commisstionFee = ArithUtil.multiply(volMoney, commissionPercent);

        //如果佣金小于最小佣金，取最小佣金值
        if(ArithUtil.compare(commisstionFee, minCommissionFee) == -1){
            commisstionFee = minCommissionFee;
        }
        rsArr[0] = commisstionFee;
        //买股票
        if (type == ApplicationConstants.STOCK_QUOTETYPE_BUY.shortValue()) {

            rsArr[1] = ArithUtil.add(volMoney, commisstionFee);

        }else if (type == ApplicationConstants.STOCK_QUOTETYPE_SELL.shortValue()) {//卖股票
            BigDecimal minTaxFee = formatStr2Decimal(minTaxFeeStr);
            BigDecimal taxPercent = formatStr2Decimal(taxPercentStr);
            //计算印花税
            BigDecimal taxFee =  ArithUtil.multiply(volMoney,  taxPercent);
            //如果印花税小于最小印花税，取最小印花税值
            if(ArithUtil.compare(taxFee, minTaxFee) == -1){
                taxFee = minTaxFee;
            }
            rsArr[1] =  ArithUtil.subtract(volMoney, ArithUtil.add(commisstionFee, taxFee));
            rsArr[2] = taxFee;
        }else{}

        return rsArr;
    }

    /**
     * 通过quote实体生成key
     * @param quote
     * @return
     */
    public static Long getQuoteKeyByQuote(StQuote quote) {
        long timeSort = Integer.parseInt(String.valueOf(quote.getDateTime()).substring(7));
        Long quotePriceForSort = 0L;
        if (quote.getQuoteType()==ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quotePriceForSort = -1 * Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + timeSort;
        } else {
            quotePriceForSort = Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + timeSort;
        }

        return quotePriceForSort;
    }

    private static BigDecimal formatStr2Decimal(String str){
        if(StringUtils.isEmpty(str))return null;
        return new BigDecimal(str);
    }
}
