package com.ryd.server.stocktrader.utils;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.business.model.*;

import java.math.BigDecimal;

/**
 * <p>标题:参数封装Dto工具类</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.utils
 * 创建人：songby
 * 创建时间：2016/4/28 17:09
 */
public class ParamBuilderDtoUtil {

    /**
     * 股票信息
     * @param stStock
     * @return
     */
    public static StStock getStStock(DiyNettyMessage.StockInfo stStock){

        StStock builder = new StStock();
        builder.setId(stStock.getId());
        builder.setStockName(stStock.getStockName());
        builder.setStockCode(stStock.getStockCode());
        builder.setOpenPrice(stStock.getOpenPrice());
        builder.setBfclosePrice(stStock.getBfclosePrice());
        builder.setCurrentPrice(stStock.getCurrentPrice());
        builder.setMaxPrice(stStock.getMaxPrice());
        builder.setMinPrice(stStock.getMinPrice());
        builder.setBiddingBuyPrice(stStock.getBiddingBuyPrice());
        builder.setBiddingSellPrice(stStock.getBiddingSellPrice());
        builder.setTradeAmount(stStock.getTradeAmount());
        builder.setTradeMoney(stStock.getTradeMoney());
        builder.setBuyOnePrice(stStock.getBuyOnePrice());
        builder.setBuyOneAmount((long) stStock.getBuyOneAmount());
        builder.setBuyTwoPrice(stStock.getBuyTwoPrice());
        builder.setBuyTwoAmount((long)stStock.getBuyTwoAmount());
        builder.setBuyThreePrice(stStock.getBuyThreePrice());
        builder.setBuyThreeAmount((long)stStock.getBuyThreeAmount());
        builder.setBuyFourPrice(stStock.getBuyFourPrice());
        builder.setBuyFourAmount((long)stStock.getBuyFourAmount());
        builder.setBuyFivePrice(stStock.getBuyFivePrice());
        builder.setBuyFiveAmount((long)stStock.getBuyFiveAmount());

        builder.setSellOnePrice(stStock.getSellOnePrice());
        builder.setSellOneAmount((long)stStock.getSellOneAmount());
        builder.setSellTwoPrice(stStock.getSellTwoPrice());
        builder.setSellTwoAmount((long)stStock.getSellTwoAmount());
        builder.setSellThreePrice(stStock.getSellThreePrice());
        builder.setSellThreeAmount((long)stStock.getSellThreeAmount());
        builder.setSellFourPrice(stStock.getSellFourPrice());
        builder.setSellFourAmount((long)stStock.getSellFourAmount());
        builder.setSellFivePrice(stStock.getSellFivePrice());
        builder.setSellFiveAmount((long)stStock.getSellFiveAmount());
        builder.setStockDate(DateUtils.formatStrToDate(stStock.getStockDate(), DateUtils.DATE_FORMAT));
        builder.setStockTime(DateUtils.formatStrToDate(stStock.getStockTime(), "HH:mm:ss"));

        return builder;
    }


    /**
     * 帐户信息
     * @param stAcc
     * @return
     */
    public static StAccount getStAccount(DiyNettyMessage.AccountInfo stAcc){

        StAccount accbuilder = new StAccount();
        accbuilder.setId(stAcc.getAccountId());
        accbuilder.setRealName(stAcc.getRealName());
        accbuilder.setAccountName(stAcc.getAccountName());
        accbuilder.setAccountNum(stAcc.getAccountNum());
        accbuilder.setTotalAssets(BigDecimal.valueOf(stAcc.getTotalAssets()));
        accbuilder.setUseMoney(BigDecimal.valueOf(stAcc.getUseMoney()));
        accbuilder.setAccountLevel((short) stAcc.getAccountLevel());
        accbuilder.setMobile(stAcc.getMobile());
        accbuilder.setSex((short) stAcc.getSex());
        accbuilder.setRemark(stAcc.getRemark());
        accbuilder.setCreatetime(stAcc.getCreatetime());

        return accbuilder;
    }

    /**
     * 仓位信息
     * @param sp
     * @return
     */
    public static StPosition getStPosition(DiyNettyMessage.PositionInfo sp){
        StPosition pbuilder = new StPosition();
        pbuilder.setStockId(sp.getStockId());
        pbuilder.setAccountId(sp.getAccountId());
        pbuilder.setAmount((long) sp.getAmount());
        pbuilder.setMarketAmount((long) sp.getMarketAmount());
        pbuilder.setAvgPrice(BigDecimal.valueOf(sp.getAvgPrice()));
        pbuilder.setStatus((short) sp.getStatus());
        pbuilder.setPositionId(sp.getPositionId());
        return pbuilder;
    }

    /**
     * 报价信息
     * @param q1
     * @return
     */
    public static StQuote getStQuote(DiyNettyMessage.QuoteInfo q1){

        StQuote qbuiler =  new StQuote();
        qbuiler.setStockId(q1.getStockId());
        qbuiler.setAccountId(q1.getAccountId());
        qbuiler.setQuoteId(q1.getQuoteId());
        qbuiler.setQuotePrice(BigDecimal.valueOf(q1.getStockPrice()));
        qbuiler.setQuoteType((short) q1.getQuoteType());
        qbuiler.setAmount((long) q1.getAmount());
        qbuiler.setCurrentAmount((long) q1.getCurrentAmount());
        qbuiler.setStatus((short) q1.getStatus());
        qbuiler.setFrozeMoney(BigDecimal.valueOf(q1.getFrozeMoney()));
        qbuiler.setCommissionFee(BigDecimal.valueOf(q1.getCommissionFee()));
        qbuiler.setDateTime(q1.getDateTime());

        return qbuiler;
    }

    /**
     * 成交记录信息
     * @param r1
     * @return
     */
    public static StTradeRecord getStTradeRecord(DiyNettyMessage.TradeRecordInfo r1){

        StTradeRecord tbuiler = new StTradeRecord();
        tbuiler.setStockId(r1.getStockCode());
        tbuiler.setAmount((long) r1.getAmount());
        tbuiler.setQuotePrice(BigDecimal.valueOf(r1.getDealMoney()));
        if(r1.getDealType()==ApplicationConstants.STOCK_QUOTETYPE_BUY){
            tbuiler.setBuyerAccountId(r1.getAccountId());
            tbuiler.setBuyFee(BigDecimal.valueOf(r1.getDealFee()));
        }else {
            tbuiler.setSellerAccountId(r1.getAccountId());
            tbuiler.setSellFee(BigDecimal.valueOf(r1.getDealFee()));
            tbuiler.setDealTax(BigDecimal.valueOf(r1.getDealTax()));
        }
        tbuiler.setDateTime(DateUtils.formatStrToDate(r1.getDealDate()+" "+r1.getDealTime(),DateUtils.TIME_FORMAT).getTime());
        return tbuiler;
    }

    /**
     * 资金流水信息
     * @param journal
     * @return
     */
    public static StMoneyJournal getStMoneyJournal(DiyNettyMessage.MoneyJournalInfo journal){

        StMoneyJournal mbuiler = new StMoneyJournal();
        mbuiler.setAccountId(journal.getAccountId());
        mbuiler.setStockId(journal.getStockCode());
        mbuiler.setAmount((long) journal.getAmount());
        mbuiler.setDealType((short) journal.getDealType());
        mbuiler.setDealMoney(BigDecimal.valueOf(journal.getDealMoney()));
        mbuiler.setDealFee(BigDecimal.valueOf(journal.getDealFee()));
        mbuiler.setDealTax(BigDecimal.valueOf(journal.getDealTax()));

        return mbuiler;
    }


}
