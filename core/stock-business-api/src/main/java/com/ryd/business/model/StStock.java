package com.ryd.business.model;

import com.bugull.mongo.BuguEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题:股票信息</p>
 * <p>描述:股票信息</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
@Entity
public class StStock implements Serializable,BuguEntity {

    private static final long serialVersionUID = -5808387262109584648L;

    @Id
    private String stockId;
    //股票名称
    private String stockName;
    //股票代码
    private String stockCode;
    //今日开盘价
    private Double openPrice;
    //昨日收盘价
    private Double bfclosePrice;
    //现价
    private Double currentPrice;
    //今日最高价
    private Double maxPrice;
    //今日最低价
    private Double minPrice;
    //竞买价
    private Double biddingBuyPrice;
    //竞卖价
    private Double biddingSellPrice;
    //成交量
    private Long tradeAmount;
    //成交金额
    private Double tradeMoney;
    //买一报价
    private Double buyOnePrice;
    //买一股票数
    private Long buyOneAmount;

    private Double buyTwoPrice;

    private Long buyTwoAmount;

    private Double buyThreePrice;

    private Long buyThreeAmount;

    private Double buyFourPrice;

    private Long buyFourAmount;

    private Double buyFivePrice;

    private Long buyFiveAmount;
    //卖一价格
    private Double sellOnePrice;
    //卖一股票数
    private Long sellOneAmount;

    private Double sellTwoPrice;

    private Long sellTwoAmount;

    private Double sellThreePrice;

    private Long sellThreeAmount;

    private Double sellFourPrice;

    private Long sellFourAmount;

    private Double sellFivePrice;

    private Long sellFiveAmount;

    private Date stockDate;

    private String stockTime;
    // 成交总量
    private Long tradeTotalAmount;
    // 成交总金额
    private Double tradeTotalMoney;
    // 最近成交笔数
    private String recentlyTradeRecord;
    // 涨跌
    private double riseFallMoney;
    // 涨跌百分比
    private double riseFallPercent;
    // 换手率
    private double turnoverRatio;
    // 市盈率
    private double priceEarningRatio;
    // 振幅
    private double amplitude;
    // 流通市值
    private double circulationCapitalization;
    // 总市值
    private double marketCapitalization;
    // 市净率
    private double pbRatio;
    // 外盘
    private int sellVol;
    // 内盘
    private int buyVol;
    // 涨停价
    private double riseUpPrice;
    // 跌停价
    private double fallDownPrice;

    public double getRiseUpPrice() {
        return riseUpPrice;
    }

    public void setRiseUpPrice(double riseUpPrice) {
        this.riseUpPrice = riseUpPrice;
    }

    public double getFallDownPrice() {
        return fallDownPrice;
    }

    public void setFallDownPrice(double fallDownPrice) {
        this.fallDownPrice = fallDownPrice;
    }

    public String getRecentlyTradeRecord() {
        return recentlyTradeRecord;
    }

    public void setRecentlyTradeRecord(String recentlyTradeRecord) {
        this.recentlyTradeRecord = recentlyTradeRecord;
    }

    public double getRiseFallMoney() {
        return riseFallMoney;
    }

    public void setRiseFallMoney(double riseFallMoney) {
        this.riseFallMoney = riseFallMoney;
    }

    public double getRiseFallPercent() {
        return riseFallPercent;
    }

    public void setRiseFallPercent(double riseFallPercent) {
        this.riseFallPercent = riseFallPercent;
    }

    public double getTurnoverRatio() {
        return turnoverRatio;
    }

    public void setTurnoverRatio(double turnoverRatio) {
        this.turnoverRatio = turnoverRatio;
    }

    public double getPriceEarningRatio() {
        return priceEarningRatio;
    }

    public void setPriceEarningRatio(double priceEarningRatio) {
        this.priceEarningRatio = priceEarningRatio;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getCirculationCapitalization() {
        return circulationCapitalization;
    }

    public void setCirculationCapitalization(double circulationCapitalization) {
        this.circulationCapitalization = circulationCapitalization;
    }

    public double getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(double marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public double getPbRatio() {
        return pbRatio;
    }

    public void setPbRatio(double pbRatio) {
        this.pbRatio = pbRatio;
    }

    public int getSellVol() {
        return sellVol;
    }

    public void setSellVol(int sellVol) {
        this.sellVol = sellVol;
    }

    public int getBuyVol() {
        return buyVol;
    }

    public void setBuyVol(int buyVol) {
        this.buyVol = buyVol;
    }

    public Long getTradeTotalAmount() {
        return tradeTotalAmount;
    }

    public void setTradeTotalAmount(Long tradeTotalAmount) {
        this.tradeTotalAmount = tradeTotalAmount;
    }

    public Double getTradeTotalMoney() {
        return tradeTotalMoney;
    }

    public void setTradeTotalMoney(Double tradeTotalMoney) {
        this.tradeTotalMoney = tradeTotalMoney;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getBfclosePrice() {
        return bfclosePrice;
    }

    public void setBfclosePrice(Double bfclosePrice) {
        this.bfclosePrice = bfclosePrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getBiddingBuyPrice() {
        return biddingBuyPrice;
    }

    public void setBiddingBuyPrice(Double biddingBuyPrice) {
        this.biddingBuyPrice = biddingBuyPrice;
    }

    public Double getBiddingSellPrice() {
        return biddingSellPrice;
    }

    public void setBiddingSellPrice(Double biddingSellPrice) {
        this.biddingSellPrice = biddingSellPrice;
    }

    public Long getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Long tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(Double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Double getBuyOnePrice() {
        return buyOnePrice;
    }

    public void setBuyOnePrice(Double buyOnePrice) {
        this.buyOnePrice = buyOnePrice;
    }

    public Long getBuyOneAmount() {
        return buyOneAmount;
    }

    public void setBuyOneAmount(Long buyOneAmount) {
        this.buyOneAmount = buyOneAmount;
    }

    public Double getBuyTwoPrice() {
        return buyTwoPrice;
    }

    public void setBuyTwoPrice(Double buyTwoPrice) {
        this.buyTwoPrice = buyTwoPrice;
    }

    public Long getBuyTwoAmount() {
        return buyTwoAmount;
    }

    public void setBuyTwoAmount(Long buyTwoAmount) {
        this.buyTwoAmount = buyTwoAmount;
    }

    public Double getBuyThreePrice() {
        return buyThreePrice;
    }

    public void setBuyThreePrice(Double buyThreePrice) {
        this.buyThreePrice = buyThreePrice;
    }

    public Long getBuyThreeAmount() {
        return buyThreeAmount;
    }

    public void setBuyThreeAmount(Long buyThreeAmount) {
        this.buyThreeAmount = buyThreeAmount;
    }

    public Double getBuyFourPrice() {
        return buyFourPrice;
    }

    public void setBuyFourPrice(Double buyFourPrice) {
        this.buyFourPrice = buyFourPrice;
    }

    public Long getBuyFourAmount() {
        return buyFourAmount;
    }

    public void setBuyFourAmount(Long buyFourAmount) {
        this.buyFourAmount = buyFourAmount;
    }

    public Double getBuyFivePrice() {
        return buyFivePrice;
    }

    public void setBuyFivePrice(Double buyFivePrice) {
        this.buyFivePrice = buyFivePrice;
    }

    public Long getBuyFiveAmount() {
        return buyFiveAmount;
    }

    public void setBuyFiveAmount(Long buyFiveAmount) {
        this.buyFiveAmount = buyFiveAmount;
    }

    public Double getSellOnePrice() {
        return sellOnePrice;
    }

    public void setSellOnePrice(Double sellOnePrice) {
        this.sellOnePrice = sellOnePrice;
    }

    public Long getSellOneAmount() {
        return sellOneAmount;
    }

    public void setSellOneAmount(Long sellOneAmount) {
        this.sellOneAmount = sellOneAmount;
    }

    public Double getSellTwoPrice() {
        return sellTwoPrice;
    }

    public void setSellTwoPrice(Double sellTwoPrice) {
        this.sellTwoPrice = sellTwoPrice;
    }

    public Long getSellTwoAmount() {
        return sellTwoAmount;
    }

    public void setSellTwoAmount(Long sellTwoAmount) {
        this.sellTwoAmount = sellTwoAmount;
    }

    public Double getSellThreePrice() {
        return sellThreePrice;
    }

    public void setSellThreePrice(Double sellThreePrice) {
        this.sellThreePrice = sellThreePrice;
    }

    public Long getSellThreeAmount() {
        return sellThreeAmount;
    }

    public void setSellThreeAmount(Long sellThreeAmount) {
        this.sellThreeAmount = sellThreeAmount;
    }

    public Double getSellFourPrice() {
        return sellFourPrice;
    }

    public void setSellFourPrice(Double sellFourPrice) {
        this.sellFourPrice = sellFourPrice;
    }

    public Long getSellFourAmount() {
        return sellFourAmount;
    }

    public void setSellFourAmount(Long sellFourAmount) {
        this.sellFourAmount = sellFourAmount;
    }

    public Double getSellFivePrice() {
        return sellFivePrice;
    }

    public void setSellFivePrice(Double sellFivePrice) {
        this.sellFivePrice = sellFivePrice;
    }

    public Long getSellFiveAmount() {
        return sellFiveAmount;
    }

    public void setSellFiveAmount(Long sellFiveAmount) {
        this.sellFiveAmount = sellFiveAmount;
    }

    public Date getStockDate() {
        return stockDate;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    public String getStockTime() {
        return stockTime;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    @Override
    public void setId(String stockId) {
        this.stockId = stockId;
    }

    @Override
    public String getId() {
        return stockId;
    }
}