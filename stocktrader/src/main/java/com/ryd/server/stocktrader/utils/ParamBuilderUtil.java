package com.ryd.server.stocktrader.utils;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.business.model.*;

/**
 * <p>标题:参数封闭工具类</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.utils
 * 创建人：songby
 * 创建时间：2016/4/28 17:09
 */
public class ParamBuilderUtil {

    /**
     * 股票信息
     * @param stStock
     * @return
     */
    public static DiyNettyMessage.StockInfo.Builder getStockInfoBuilder(StStock stStock){

        DiyNettyMessage.StockInfo.Builder builder = DiyNettyMessage.StockInfo.newBuilder();
        builder.setId(stStock.getId());
        builder.setStockName(stStock.getStockName());
        builder.setStockCode(stStock.getStockCode());
        builder.setStockPrice(stStock.getCurrentPrice().doubleValue());
        builder.setOpenPrice(stStock.getOpenPrice().doubleValue());
        builder.setBfclosePrice(stStock.getBfclosePrice().doubleValue());
        builder.setCurrentPrice(stStock.getCurrentPrice().doubleValue());
        builder.setMaxPrice(stStock.getMaxPrice().doubleValue());
        builder.setMinPrice(stStock.getMinPrice().doubleValue());
        builder.setBiddingBuyPrice(stStock.getBiddingBuyPrice().doubleValue());
        builder.setBiddingSellPrice(stStock.getBiddingSellPrice().doubleValue());
        builder.setTradeAmount(stStock.getTradeAmount());
        builder.setTradeMoney(stStock.getTradeMoney().doubleValue());
        builder.setBuyOnePrice(stStock.getBuyOnePrice().doubleValue());
        builder.setBuyOneAmount(stStock.getBuyOneAmount().intValue());
        builder.setBuyTwoPrice(stStock.getBuyTwoPrice().doubleValue());
        builder.setBuyTwoAmount(stStock.getBuyTwoAmount().intValue());
        builder.setBuyThreePrice(stStock.getBuyThreePrice().doubleValue());
        builder.setBuyThreeAmount(stStock.getBuyThreeAmount().intValue());
        builder.setBuyFourPrice(stStock.getBuyFourPrice().doubleValue());
        builder.setBuyFourAmount(stStock.getBuyFourAmount().intValue());
        builder.setBuyFivePrice(stStock.getBuyFivePrice().doubleValue());
        builder.setBuyFiveAmount(stStock.getBuyFiveAmount().intValue());

        builder.setSellOnePrice(stStock.getSellOnePrice().doubleValue());
        builder.setSellOneAmount(stStock.getSellOneAmount().intValue());
        builder.setSellTwoPrice(stStock.getSellTwoPrice().doubleValue());
        builder.setSellTwoAmount(stStock.getSellTwoAmount().intValue());
        builder.setSellThreePrice(stStock.getSellThreePrice().doubleValue());
        builder.setSellThreeAmount(stStock.getSellThreeAmount().intValue());
        builder.setSellFourPrice(stStock.getSellFourPrice().doubleValue());
        builder.setSellFourAmount(stStock.getSellFourAmount().intValue());
        builder.setSellFivePrice(stStock.getSellFivePrice().doubleValue());
        builder.setSellFiveAmount(stStock.getSellFiveAmount().intValue());
//        builder.setStockDate(DateUtils.formatDateToStr(stStock.getStockDate(), DateUtils.DATE_FORMAT));
//        builder.setStockTime(DateUtils.formatDateToStr(stStock.getStockTime(), "HH:mm:ss"));

        return builder;
    }

    /**
     * 股票价格信息
     * @param stStock
     * @return
     */
    public static DiyNettyMessage.StockPriceInfo.Builder getStockPriceInfoBuilder(StStock stStock){
        DiyNettyMessage.StockPriceInfo.Builder builder = DiyNettyMessage.StockPriceInfo.newBuilder();
        builder.setId(stStock.getId());
        builder.setStockPrice(stStock.getCurrentPrice().doubleValue());
        builder.setStockTime(DateUtils.formatDateToStr(stStock.getStockTime(), "HH:mm:ss"));
        return builder;
    }

    /**
     * 帐户信息
     * @param stAcc
     * @return
     */
    public static DiyNettyMessage.AccountInfo.Builder getAccountInfoBuilder(StAccount stAcc){

        DiyNettyMessage.AccountInfo.Builder accbuilder = DiyNettyMessage.AccountInfo.newBuilder();
        accbuilder.setAccountId(stAcc.getId());
        accbuilder.setRealName(stAcc.getRealName());
        accbuilder.setAccountName(stAcc.getAccountName());
        accbuilder.setAccountNum(stAcc.getAccountNum());
        accbuilder.setTotalAssets(stAcc.getTotalAssets().longValue());
        accbuilder.setUseMoney(stAcc.getUseMoney().intValue());
        accbuilder.setAccountLevel(stAcc.getAccountLevel());
        accbuilder.setMobile(stAcc.getMobile());
        accbuilder.setSex(stAcc.getSex());
        accbuilder.setRemark(stAcc.getRemark());
        accbuilder.setCreatetime(stAcc.getCreatetime());

        return accbuilder;
    }

    /**
     * 仓位信息
     * @param sp
     * @return
     */
    public static DiyNettyMessage.PositionInfo.Builder getPositionInfoBuilder(StPosition sp){
        DiyNettyMessage.PositionInfo.Builder pbuilder = DiyNettyMessage.PositionInfo.newBuilder();
        pbuilder.setStockId(sp.getStockId());
        pbuilder.setStockName("");
        pbuilder.setAccountId(sp.getAccountId());
//      pbuilder.setStockPrice();
        pbuilder.setAmount(sp.getAmount().intValue());
        pbuilder.setMarketAmount(sp.getMarketAmount().intValue());
        pbuilder.setAvgPrice(sp.getAvgPrice().doubleValue());
        pbuilder.setStatus(sp.getStatus().intValue());
        pbuilder.setPositionId(sp.getPositionId());

        return pbuilder;
    }

    /**
     * 报价信息
     * @param q1
     * @return
     */
    public static DiyNettyMessage.QuoteInfo.Builder getQuoteInfoBuilder(StQuote q1){

        DiyNettyMessage.QuoteInfo.Builder qbuiler =  DiyNettyMessage.QuoteInfo.newBuilder();
        qbuiler.setStockId(q1.getStockId());
        qbuiler.setStockName("");
        qbuiler.setAccountId(q1.getAccountId());
        qbuiler.setQuoteId(q1.getQuoteId());
        qbuiler.setStockPrice(q1.getQuotePrice().doubleValue());
        qbuiler.setQuoteType(q1.getQuoteType());
        qbuiler.setAmount(q1.getAmount().intValue());
        qbuiler.setCurrentAmount(q1.getCurrentAmount().intValue());
        qbuiler.setStatus(q1.getStatus());
        qbuiler.setFrozeMoney(q1.getFrozeMoney().doubleValue());
        qbuiler.setCommissionFee(q1.getCommissionFee().doubleValue());
        qbuiler.setDateTime(q1.getDateTime());

        return qbuiler;
    }

    /**
     * 成交记录信息
     * @param r1
     * @param accountId
     * @return
     */
    public static DiyNettyMessage.TradeRecordInfo.Builder getTradeRecordInfoBuilder(StTradeRecord r1, String accountId){

        DiyNettyMessage.TradeRecordInfo.Builder tbuiler = DiyNettyMessage.TradeRecordInfo.newBuilder();
        tbuiler.setAccountId(accountId);
        tbuiler.setStockCode(r1.getStockId());
        tbuiler.setStockName("");
        tbuiler.setAmount(r1.getAmount().intValue());
        if(accountId.equals(r1.getSellerAccountId())){
            tbuiler.setDealType(ApplicationConstants.STOCK_QUOTETYPE_SELL);
            tbuiler.setDealFee(r1.getSellFee().doubleValue());
            tbuiler.setDealTax(r1.getDealTax().doubleValue());
        } else {
            tbuiler.setDealType(ApplicationConstants.STOCK_QUOTETYPE_BUY);
            tbuiler.setDealFee(r1.getBuyFee().doubleValue());
        }

        tbuiler.setDealDate(DateUtils.formatLongToStr(r1.getDateTime(), DateUtils.DATE_FORMAT));
        tbuiler.setDealTime(DateUtils.formatLongToStr(r1.getDateTime(), "HH:mm:ss"));

        return tbuiler;
    }

    /**
     * 资金流水信息
     * @param journal
     * @return
     */
    public static DiyNettyMessage.MoneyJournalInfo.Builder getMoneyJournalInfoBuilder(StMoneyJournal journal){

        DiyNettyMessage.MoneyJournalInfo.Builder mbuiler = DiyNettyMessage.MoneyJournalInfo.newBuilder();
        mbuiler.setAccountId(journal.getAccountId());
        mbuiler.setStockCode(journal.getStockId());
        mbuiler.setStockName("");
        mbuiler.setAmount(journal.getAmount().intValue());
        mbuiler.setDealType(journal.getDealType().intValue());
        mbuiler.setDealMoney(journal.getDealMoney().doubleValue());
        mbuiler.setDealFee(journal.getDealFee().doubleValue());
        mbuiler.setDealTax(journal.getDealTax().doubleValue());
        mbuiler.setDealDate(DateUtils.formatLongToStr(journal.getDateTime(), DateUtils.DATE_FORMAT));
        mbuiler.setDealTime(DateUtils.formatLongToStr(journal.getDateTime(), "HH:mm:ss"));

        return mbuiler;
    }

}