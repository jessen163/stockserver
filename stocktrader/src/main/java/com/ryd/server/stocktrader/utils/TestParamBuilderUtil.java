package com.ryd.server.stocktrader.utils;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.model.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.utils
 * 创建人：songby
 * 创建时间：2016/4/28 17:09
 */
public class TestParamBuilderUtil {

    //注册
    public static DiyNettyMessage.NettyMessage.Builder register(String accountName,String accountNum, String password){
        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
        builder.setId(ApplicationConstants.NETTYMESSAGE_ID_REGISTER);
        builder.setStatus(0);
        builder.setKey("1");

        DiyNettyMessage.AccountInfo.Builder paramBuilder = DiyNettyMessage.AccountInfo.newBuilder();

        paramBuilder.setRealName("realName");
        paramBuilder.setAccountName(accountName);
        paramBuilder.setAccountNum(accountNum);
        paramBuilder.setPassword(password);
        paramBuilder.setTotalAssets(10000000);
        paramBuilder.setUseMoney(10000000);
        paramBuilder.setAccountLevel(1);
        paramBuilder.setMobile("15911215736");
        paramBuilder.setSex(1);
        paramBuilder.setRemark("realName");

        builder.addAccountInfo(paramBuilder);

        return builder;
    }

    //登录
    public static DiyNettyMessage.NettyMessage.Builder login(String accountNum, String password){

        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
        builder.setId(ApplicationConstants.NETTYMESSAGE_ID_LOGIN);
        builder.setStatus(0);
        builder.setKey("1");

        DiyNettyMessage.AccountInfo.Builder paramBuilder = DiyNettyMessage.AccountInfo.newBuilder();
        paramBuilder.setAccountNum(accountNum);
        paramBuilder.setPassword(password);
        builder.addAccountInfo(paramBuilder);

        return builder;
    }

    //
    public static DiyNettyMessage.NettyMessage.Builder getStockInfoBuilder(int paramType,int type, String stockId){

        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();

        builder.setId(paramType);
        builder.setStatus(0);
        builder.setKey("1");
        builder.setType(type);

        if(StringUtils.isNotBlank(stockId)) {
            DiyNettyMessage.StockInfo.Builder paramBuilder = DiyNettyMessage.StockInfo.newBuilder();
            paramBuilder.setId(stockId);
            paramBuilder.setStockCode(stockId);

            builder.addStockInfo(paramBuilder);
        }

        return builder;
    }

    //报价
    public static DiyNettyMessage.NettyMessage.Builder getQuote(String stockId,String accountId,double price,int type, int amount){

        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();

        builder.setId(ApplicationConstants.NETTYMESSAGE_ID_QUOTE);
        builder.setStatus(0);
        builder.setKey("1");
        builder.setAccountId(accountId);

        DiyNettyMessage.QuoteInfo.Builder paramBuilder =  DiyNettyMessage.QuoteInfo.newBuilder();
        paramBuilder.setStockId(stockId);
        paramBuilder.setAccountId(accountId);
        paramBuilder.setStockPrice(price);
        paramBuilder.setQuoteType(type);
        paramBuilder.setAmount(amount);

        builder.addQuoteInfo(paramBuilder);

        return builder;
    }

    //撤单
    public static DiyNettyMessage.NettyMessage.Builder getRevoke(String quoteId,String accountId){

        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();

        builder.setId(ApplicationConstants.NETTYMESSAGE_ID_WITHDRAWORDER);
        builder.setStatus(0);
        builder.setKey("1");
        builder.setAccountId(accountId);

        DiyNettyMessage.QuoteInfo.Builder paramBuilder =  DiyNettyMessage.QuoteInfo.newBuilder();
        paramBuilder.setQuoteId(quoteId);
        paramBuilder.setAccountId(accountId);

        builder.addQuoteInfo(paramBuilder);

        return builder;
    }

    //我的股份，我的报价，我的成交记录，我的资金流水
    public static DiyNettyMessage.NettyMessage.Builder getAccount(int type, String accountId,Long startTime,Long endTime){

        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();

        builder.setId(type);
        builder.setStatus(0);
        builder.setKey("1");
        builder.setAccountId(accountId);
        builder.setOffset(0);
        builder.setSize(Integer.MAX_VALUE);
        builder.setStartTime(startTime);
        builder.setEndTime(endTime);

        return builder;
    }
}
