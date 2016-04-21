package com.ryd.business.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>标题:交易记录</p>
 * <p>描述:交易记录</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StTradeRecord implements Serializable {

    private static final long serialVersionUID = -8312639583014301540L;

    private String recordId;
    //卖家帐户ID
    private String sellerAccountId;
    //买家帐户ID
    private String buyerAccountId;
    //交易股票ID
    private String stockId;
    //交易价格
    private BigDecimal quotePrice;
    //交易股票数量
    private Long amount;
    //交易时间
    private Long dateTime;
    //交易买家佣金
    private BigDecimal buyFee;
    //交易卖家佣金
    private BigDecimal sellFee;
    //交易印花税
    private BigDecimal dealTax;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(String sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public String getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(String buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(BigDecimal buyFee) {
        this.buyFee = buyFee;
    }

    public BigDecimal getSellFee() {
        return sellFee;
    }

    public void setSellFee(BigDecimal sellFee) {
        this.sellFee = sellFee;
    }

    public BigDecimal getDealTax() {
        return dealTax;
    }

    public void setDealTax(BigDecimal dealTax) {
        this.dealTax = dealTax;
    }
}