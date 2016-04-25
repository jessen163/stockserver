package com.ryd.business.model;

import com.bugull.mongo.BuguEntity;
import com.bugull.mongo.annotations.Entity;
import com.bugull.mongo.annotations.Id;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal openPrice;
    //昨日收盘价
    private BigDecimal bfclosePrice;
    //现价
    private BigDecimal currentPrice;
    //今日最高价
    private BigDecimal maxPrice;
    //今日最低价
    private BigDecimal minPrice;
    //竞买价
    private BigDecimal biddingBuyPrice;
    //竞卖价
    private BigDecimal biddingSellPrice;
    //成交量
    private Long tradeAmount;
    //成交金额
    private BigDecimal tradeMoney;
    //买一报价
    private BigDecimal buyOnePrice;
    //买一股票数
    private Long buyOneAmount;

    private BigDecimal buyTwoPrice;

    private Long buyTwoAmount;

    private BigDecimal buyThreePrice;

    private Long buyThreeAmount;

    private BigDecimal buyFourPrice;

    private Long buyFourAmount;

    private BigDecimal buyFivePrice;

    private Long buyFiveAmount;
    //卖一价格
    private BigDecimal sellOnePrice;
    //卖一股票数
    private Long sellOneAmount;

    private BigDecimal sellTwoPrice;

    private Long sellTwoAmount;

    private BigDecimal sellThreePrice;

    private Long sellThreeAmount;

    private BigDecimal sellFourPrice;

    private Long sellFourAmount;

    private BigDecimal sellFivePrice;

    private Long sellFiveAmount;

    private Date stockDate;

    private Date stockTime;

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

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getBfclosePrice() {
        return bfclosePrice;
    }

    public void setBfclosePrice(BigDecimal bfclosePrice) {
        this.bfclosePrice = bfclosePrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getBiddingBuyPrice() {
        return biddingBuyPrice;
    }

    public void setBiddingBuyPrice(BigDecimal biddingBuyPrice) {
        this.biddingBuyPrice = biddingBuyPrice;
    }

    public BigDecimal getBiddingSellPrice() {
        return biddingSellPrice;
    }

    public void setBiddingSellPrice(BigDecimal biddingSellPrice) {
        this.biddingSellPrice = biddingSellPrice;
    }

    public Long getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Long tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public BigDecimal getBuyOnePrice() {
        return buyOnePrice;
    }

    public void setBuyOnePrice(BigDecimal buyOnePrice) {
        this.buyOnePrice = buyOnePrice;
    }

    public Long getBuyOneAmount() {
        return buyOneAmount;
    }

    public void setBuyOneAmount(Long buyOneAmount) {
        this.buyOneAmount = buyOneAmount;
    }

    public BigDecimal getBuyTwoPrice() {
        return buyTwoPrice;
    }

    public void setBuyTwoPrice(BigDecimal buyTwoPrice) {
        this.buyTwoPrice = buyTwoPrice;
    }

    public Long getBuyTwoAmount() {
        return buyTwoAmount;
    }

    public void setBuyTwoAmount(Long buyTwoAmount) {
        this.buyTwoAmount = buyTwoAmount;
    }

    public BigDecimal getBuyThreePrice() {
        return buyThreePrice;
    }

    public void setBuyThreePrice(BigDecimal buyThreePrice) {
        this.buyThreePrice = buyThreePrice;
    }

    public Long getBuyThreeAmount() {
        return buyThreeAmount;
    }

    public void setBuyThreeAmount(Long buyThreeAmount) {
        this.buyThreeAmount = buyThreeAmount;
    }

    public BigDecimal getBuyFourPrice() {
        return buyFourPrice;
    }

    public void setBuyFourPrice(BigDecimal buyFourPrice) {
        this.buyFourPrice = buyFourPrice;
    }

    public Long getBuyFourAmount() {
        return buyFourAmount;
    }

    public void setBuyFourAmount(Long buyFourAmount) {
        this.buyFourAmount = buyFourAmount;
    }

    public BigDecimal getBuyFivePrice() {
        return buyFivePrice;
    }

    public void setBuyFivePrice(BigDecimal buyFivePrice) {
        this.buyFivePrice = buyFivePrice;
    }

    public Long getBuyFiveAmount() {
        return buyFiveAmount;
    }

    public void setBuyFiveAmount(Long buyFiveAmount) {
        this.buyFiveAmount = buyFiveAmount;
    }

    public BigDecimal getSellOnePrice() {
        return sellOnePrice;
    }

    public void setSellOnePrice(BigDecimal sellOnePrice) {
        this.sellOnePrice = sellOnePrice;
    }

    public Long getSellOneAmount() {
        return sellOneAmount;
    }

    public void setSellOneAmount(Long sellOneAmount) {
        this.sellOneAmount = sellOneAmount;
    }

    public BigDecimal getSellTwoPrice() {
        return sellTwoPrice;
    }

    public void setSellTwoPrice(BigDecimal sellTwoPrice) {
        this.sellTwoPrice = sellTwoPrice;
    }

    public Long getSellTwoAmount() {
        return sellTwoAmount;
    }

    public void setSellTwoAmount(Long sellTwoAmount) {
        this.sellTwoAmount = sellTwoAmount;
    }

    public BigDecimal getSellThreePrice() {
        return sellThreePrice;
    }

    public void setSellThreePrice(BigDecimal sellThreePrice) {
        this.sellThreePrice = sellThreePrice;
    }

    public Long getSellThreeAmount() {
        return sellThreeAmount;
    }

    public void setSellThreeAmount(Long sellThreeAmount) {
        this.sellThreeAmount = sellThreeAmount;
    }

    public BigDecimal getSellFourPrice() {
        return sellFourPrice;
    }

    public void setSellFourPrice(BigDecimal sellFourPrice) {
        this.sellFourPrice = sellFourPrice;
    }

    public Long getSellFourAmount() {
        return sellFourAmount;
    }

    public void setSellFourAmount(Long sellFourAmount) {
        this.sellFourAmount = sellFourAmount;
    }

    public BigDecimal getSellFivePrice() {
        return sellFivePrice;
    }

    public void setSellFivePrice(BigDecimal sellFivePrice) {
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

    public Date getStockTime() {
        return stockTime;
    }

    public void setStockTime(Date stockTime) {
        this.stockTime = stockTime;
    }

    @Override
    public void setId(String s) {
        this.stockId = s;
    }

    @Override
    public String getId() {
        return this.stockId;
    }
}