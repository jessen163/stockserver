package com.ryd.server.stocktrader.utils;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.business.dto.StStockTurnoverDTO;
import com.ryd.business.dto.StockPriceDTO;
import com.ryd.business.dto.StockTradeAmountDTO;
import com.ryd.business.model.*;

/**
 * <p>标题:参数封闭工具类</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.utils
 * 创建人：songby
 * 创建时间：2016/4/28 17:09
 */
public class ParamBuilderUtil {

    public static DiyNettyMessage.StockConfigInfo.Builder getStockConfigInfoBuilder(StStockConfig stStockConfig){

        DiyNettyMessage.StockConfigInfo.Builder builder = DiyNettyMessage.StockConfigInfo.newBuilder();
        builder.setId(stStockConfig.getId());
        builder.setStockName(stStockConfig.getStockName());
        builder.setStockCode(stStockConfig.getStockCode());
        builder.setStockType(stStockConfig.getStockType());
        builder.setBoardType(stStockConfig.getBoardType());

        return builder;
    }

    /**
     * 股票信息
     * @param stStock
     * @return
     */
    public static DiyNettyMessage.StockInfo.Builder getStockInfoBuilder(StStock stStock){

        DiyNettyMessage.StockInfo.Builder builder = DiyNettyMessage.StockInfo.newBuilder();
        builder.setId(stStock.getId() == null ? "" : stStock.getId());
        builder.setStockName(stStock.getStockName() == null ? "" : stStock.getStockName());
        builder.setStockCode(stStock.getStockCode() == null ? "" : stStock.getStockCode());
        builder.setStockPrice(stStock.getCurrentPrice() == null ? 0 : stStock.getCurrentPrice().doubleValue());
        builder.setOpenPrice(stStock.getOpenPrice() == null ? 0 : stStock.getOpenPrice().doubleValue());
        builder.setBfclosePrice(stStock.getBfclosePrice() == null ? 0 : stStock.getBfclosePrice().doubleValue());
        builder.setCurrentPrice(stStock.getCurrentPrice() == null ? 0 : stStock.getCurrentPrice().doubleValue());
        builder.setMaxPrice(stStock.getMaxPrice() == null ? 0 : stStock.getMaxPrice().doubleValue());
        builder.setMinPrice(stStock.getMinPrice() == null ? 0 : stStock.getMinPrice().doubleValue());
        builder.setBiddingBuyPrice(stStock.getBiddingBuyPrice() == null ? 0 : stStock.getBiddingBuyPrice().doubleValue());
        builder.setBiddingSellPrice(stStock.getBiddingSellPrice() == null ? 0 : stStock.getBiddingSellPrice().doubleValue());
        builder.setTradeAmount(stStock.getTradeAmount() == null ? 0 : stStock.getTradeAmount());
        builder.setTradeMoney(stStock.getTradeMoney() == null ? 0 : stStock.getTradeMoney().doubleValue());
        builder.setBuyOnePrice(stStock.getBuyOnePrice() == null ? 0 : stStock.getBuyOnePrice().doubleValue());
        builder.setBuyOneAmount(stStock.getBuyOneAmount() == null ? 0 : stStock.getBuyOneAmount().intValue());
        builder.setBuyTwoPrice(stStock.getBuyTwoPrice() == null ? 0 : stStock.getBuyTwoPrice().doubleValue());
        builder.setBuyTwoAmount(stStock.getBuyTwoAmount() == null ? 0 : stStock.getBuyTwoAmount().intValue());
        builder.setBuyThreePrice(stStock.getBuyThreePrice() == null ? 0 : stStock.getBuyThreePrice().doubleValue());
        builder.setBuyThreeAmount(stStock.getBuyThreeAmount() == null ? 0 : stStock.getBuyThreeAmount().intValue());
        builder.setBuyFourPrice(stStock.getBuyFourPrice() == null ? 0 : stStock.getBuyFourPrice().doubleValue());
        builder.setBuyFourAmount(stStock.getBuyFourAmount() == null ? 0 : stStock.getBuyFourAmount().intValue());
        builder.setBuyFivePrice(stStock.getBuyFivePrice() == null ? 0 : stStock.getBuyFivePrice().doubleValue());
        builder.setBuyFiveAmount(stStock.getBuyFiveAmount() == null ? 0 : stStock.getBuyFiveAmount().intValue());

        builder.setSellOnePrice(stStock.getSellOnePrice() == null ? 0 : stStock.getSellOnePrice().doubleValue());
        builder.setSellOneAmount(stStock.getSellOneAmount() == null ? 0 : stStock.getSellOneAmount().intValue());
        builder.setSellTwoPrice(stStock.getSellTwoPrice() == null ? 0 : stStock.getSellTwoPrice().doubleValue());
        builder.setSellTwoAmount(stStock.getSellTwoAmount() == null ? 0 : stStock.getSellTwoAmount().intValue());
        builder.setSellThreePrice(stStock.getSellThreePrice() == null ? 0 : stStock.getSellThreePrice().doubleValue());
        builder.setSellThreeAmount(stStock.getSellThreeAmount() == null ? 0 : stStock.getSellThreeAmount().intValue());
        builder.setSellFourPrice(stStock.getSellFourPrice() == null ? 0 : stStock.getSellFourPrice().doubleValue());
        builder.setSellFourAmount(stStock.getSellFourAmount() == null ? 0 : stStock.getSellFourAmount().intValue());
        builder.setSellFivePrice(stStock.getSellFivePrice() == null ? 0 : stStock.getSellFivePrice().doubleValue());
        builder.setSellFiveAmount(stStock.getSellFiveAmount() == null ? 0 : stStock.getSellFiveAmount().intValue());
        builder.setStockDate(stStock.getStockDate() == null ? "" : DateUtils.formatDateToStr(stStock.getStockDate(), DateUtils.DATE_FORMAT));
        builder.setStockTime(stStock.getStockTime() == null ? "" : stStock.getStockTime());
        builder.setDealTotalAmount(stStock.getTradeTotalAmount() == null ? 0 : stStock.getTradeTotalAmount().intValue());
        builder.setDealTotalMoney(stStock.getTradeTotalMoney() == null ? 0 : stStock.getTradeTotalMoney().intValue());
        return builder;
    }

    /**
     * 股票价格信息
     * @param stockPriceDTO
     * @return
     */
    public static DiyNettyMessage.StockPriceInfo.Builder getStockPriceInfoBuilder(StockPriceDTO stockPriceDTO){
        DiyNettyMessage.StockPriceInfo.Builder builder = DiyNettyMessage.StockPriceInfo.newBuilder();
        builder.setStockPrice(stockPriceDTO.getTradePrice());
        builder.setStockTime(stockPriceDTO.getTradeTime() == null ? "" : stockPriceDTO.getTradeTime());
        return builder;
    }

    public static DiyNettyMessage.StockTradeAmountInfo.Builder getStockTradeAmountInfoBuilder(StStockTurnoverDTO stStockTurnoverDTO){
        DiyNettyMessage.StockTradeAmountInfo.Builder builder = DiyNettyMessage.StockTradeAmountInfo.newBuilder();
        builder.setTradeAmount((int) stStockTurnoverDTO.getTradeAmount());
        builder.setTradeTime(stStockTurnoverDTO.getTradeTime() == null ? "" : stStockTurnoverDTO.getTradeTime());
        return builder;
    }


    public static DiyNettyMessage.StockInfo.Builder getMonitorStockInfoBuilder(StStock stStock) {

        DiyNettyMessage.StockInfo.Builder builder = DiyNettyMessage.StockInfo.newBuilder();
        builder.setId(stStock.getId() == null ? "" : stStock.getId());
        builder.setStockName(stStock.getStockName() == null ? "" : stStock.getStockName());
        builder.setStockCode(stStock.getStockCode() == null ? "" : stStock.getStockCode());
        builder.setStockPrice(stStock.getCurrentPrice() == null ? 0 : stStock.getCurrentPrice().doubleValue());
        builder.setOpenPrice(stStock.getOpenPrice() == null ? 0 : stStock.getOpenPrice().doubleValue());
        builder.setBfclosePrice(stStock.getBfclosePrice() == null ? 0 : stStock.getBfclosePrice().doubleValue());
        builder.setCurrentPrice(stStock.getCurrentPrice() == null ? 0 : stStock.getCurrentPrice().doubleValue());
        builder.setMaxPrice(stStock.getMaxPrice() == null ? 0 : stStock.getMaxPrice().doubleValue());
        builder.setMinPrice(stStock.getMinPrice() == null ? 0 : stStock.getMinPrice().doubleValue());
        builder.setStockDate(stStock.getStockDate() == null ? "" : DateUtils.formatDateToStr(stStock.getStockDate(), DateUtils.DATE_FORMAT));
        builder.setStockTime(stStock.getStockTime() == null ? "" : stStock.getStockTime());
        builder.setDealTotalAmount(stStock.getTradeTotalAmount() == null ? 0 : stStock.getTradeTotalAmount().intValue());
        builder.setDealTotalMoney(stStock.getTradeTotalMoney() == null ? 0 : stStock.getTradeTotalMoney().intValue());
        return builder;
    }
    /**
     * 帐户信息
     * @param stAcc
     * @return
     */
    public static DiyNettyMessage.AccountInfo.Builder getAccountInfoBuilder(StAccount stAcc){

        DiyNettyMessage.AccountInfo.Builder accbuilder = DiyNettyMessage.AccountInfo.newBuilder();
        accbuilder.setAccountId(stAcc.getId() == null ? "" : stAcc.getId());
        accbuilder.setRealName(stAcc.getRealName() == null ? "" : stAcc.getRealName());
        accbuilder.setAccountName(stAcc.getAccountName() == null ? "" : stAcc.getAccountName());
        accbuilder.setAccountNum(stAcc.getAccountNum() == null ? "" : stAcc.getAccountNum());
        accbuilder.setPassword(stAcc.getPassword() == null ? "" : stAcc.getPassword());
        accbuilder.setTotalAssets(stAcc.getTotalAssets() == null ? 0 : stAcc.getTotalAssets().doubleValue());
        accbuilder.setUseMoney(stAcc.getUseMoney() == null ? 0 : stAcc.getUseMoney().doubleValue());
        accbuilder.setAccountLevel(stAcc.getAccountLevel());
        accbuilder.setMobile(stAcc.getMobile() == null ? "" : stAcc.getMobile() );
        accbuilder.setSex(stAcc.getSex());
        accbuilder.setRemark(stAcc.getRemark() == null ? "" : stAcc.getRemark());
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
        pbuilder.setAmount(sp.getAmount() == null ? 0 : sp.getAmount().intValue());
        pbuilder.setMarketAmount(sp.getMarketAmount() == null ? 0 : sp.getMarketAmount().intValue());
        pbuilder.setAvgPrice(sp.getAvgPrice() == null ? 0 : sp.getAvgPrice().doubleValue());
        pbuilder.setStatus(sp.getStatus().intValue());
        pbuilder.setPositionId(sp.getPositionId() == null ? "" : sp.getPositionId());

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
        qbuiler.setStockPrice(q1.getQuotePrice() == null ? 0 : q1.getQuotePrice().doubleValue());
        qbuiler.setQuoteType(q1.getQuoteType());
        qbuiler.setAmount(q1.getAmount() == null ? 0 : q1.getAmount().intValue());
        qbuiler.setCurrentAmount(q1.getCurrentAmount() == null ? 0 : q1.getCurrentAmount().intValue());
        qbuiler.setStatus(q1.getStatus());
        qbuiler.setFrozeMoney(q1.getFrozeMoney() == null ? 0 : q1.getFrozeMoney().doubleValue());
        qbuiler.setCommissionFee(q1.getCommissionFee() == null ? 0 : q1.getCommissionFee().doubleValue());
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
        tbuiler.setAccountId(accountId == null ? "" : accountId);
        tbuiler.setSellAccountId(r1.getSellerAccountId());
        tbuiler.setBuyAccountId(r1.getBuyerAccountId());
        tbuiler.setSellAccountNumber(r1.getSellerAccountNum() == null ? "" : r1.getSellerAccountNum());
        tbuiler.setBuyAccountNumber(r1.getBuyerAccountNum() == null ? "" : r1.getBuyerAccountNum());
        tbuiler.setStockCode(r1.getStockId());
        tbuiler.setStockName("");
        tbuiler.setAmount(r1.getAmount() == null ? 0 : r1.getAmount().intValue());
        tbuiler.setStockPrice(r1.getQuotePrice() == null ? 0 : r1.getQuotePrice().doubleValue());
        if(accountId.equals(r1.getSellerAccountId())){
            tbuiler.setDealType(ApplicationConstants.STOCK_QUOTETYPE_SELL);
        } else {
            tbuiler.setDealType(ApplicationConstants.STOCK_QUOTETYPE_BUY);
        }
        tbuiler.setDealFee(r1.getBuyFee() == null ? 0 : r1.getBuyFee().doubleValue());
        tbuiler.setDealFee(r1.getSellFee() == null ? 0 : r1.getSellFee().doubleValue());
        tbuiler.setDealTax(r1.getDealTax() == null ? 0 : r1.getDealTax().doubleValue());
        tbuiler.setDealDate(r1.getDateTime() == null ? "" : DateUtils.formatLongToStr(r1.getDateTime(), DateUtils.DATE_FORMAT));
        tbuiler.setDealTime(r1.getDateTime() == null ? "" : DateUtils.formatLongToStr(r1.getDateTime(), "HH:mm:ss"));

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
        mbuiler.setStockPrice(journal.getQuotePrice() == null ? 0 : journal.getQuotePrice().doubleValue());
        mbuiler.setAmount(journal.getAmount() == null ? 0 : journal.getAmount().intValue());
        mbuiler.setDealType(journal.getDealType() == null ? 1 : journal.getDealType().intValue());
        mbuiler.setDealMoney(journal.getDealMoney() == null ? 0 : journal.getDealMoney().doubleValue());
        mbuiler.setDealFee(journal.getDealFee() == null ? 0 : journal.getDealFee().doubleValue());
        if(journal.getDealType() == ApplicationConstants.STOCK_QUOTETYPE_SELL) {
            mbuiler.setDealTax(journal.getDealTax() == null ? 0 : journal.getDealTax().doubleValue());
        }
        mbuiler.setDealDate(journal.getDateTime() == null ? "" : DateUtils.formatLongToStr(journal.getDateTime(), DateUtils.DATE_FORMAT));
        mbuiler.setDealTime(journal.getDateTime() == null ? "" : DateUtils.formatLongToStr(journal.getDateTime(), "HH:mm:ss"));

        return mbuiler;
    }

}
