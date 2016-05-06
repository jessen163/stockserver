package com.ryd.business.service.util;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.basecommon.util.CacheConstant;
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
     * @param commissionPercent //佣金比例
     * @param taxPercent //税比例
     * @return rsArr[0] 佣金 rsArr[1] 资金数额 rsArr[2] 印花税
     */
    public static BigDecimal[] calculate(BigDecimal quotePrice, long amount, short type, String commissionPercent, String taxPercent) {

        BigDecimal[] rsArr = new BigDecimal[3];

        BigDecimal volMoney = ArithUtil.multiply(quotePrice, new BigDecimal(amount));
        //计算佣金
        rsArr[0] = ArithUtil.multiply(volMoney, new BigDecimal(commissionPercent));

        //买股票
        if (type == ApplicationConstants.STOCK_QUOTETYPE_BUY.shortValue()) {

            rsArr[1] = ArithUtil.add(volMoney, rsArr[0]);

        }else if (type == ApplicationConstants.STOCK_QUOTETYPE_SELL.shortValue()) {//卖股票
            //计算印花税
            BigDecimal taxFee =  ArithUtil.multiply(volMoney,  new BigDecimal(taxPercent));

            rsArr[1] =  ArithUtil.subtract(volMoney, ArithUtil.add(rsArr[0], taxFee));

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
}
