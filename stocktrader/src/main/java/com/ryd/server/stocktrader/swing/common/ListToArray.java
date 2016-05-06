package com.ryd.server.stocktrader.swing.common;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.business.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * <p>标题:客户端数据转换</p>
 * <p>描述:</p>
 * 包名：swing.common
 * 创建人：songby
 * 创建时间：2016/4/11 18:02
 */
public class ListToArray {


    /**
     * {"股票代码", "股票名称", "现价", "今开", "昨收", "最高", "最低", "总手", "买一","卖一" };
     * @param stockList
     * @return
     */
    public static Object[][] stockListToArray(List<StStock> stockList){
        if(CollectionUtils.isEmpty(stockList)){
            return null;
        }
        Object[][] arr = new Object[stockList.size()][10];
        for(int i=0;i<stockList.size();i++){
            StStock st = stockList.get(i);
            arr[i][0] = st.getStockCode();
            arr[i][1] = st.getStockName();
            arr[i][2] = st.getCurrentPrice();
            arr[i][3] = st.getOpenPrice();
            arr[i][4] = st.getBfclosePrice();
            arr[i][5] = st.getMaxPrice();
            arr[i][6] = st.getMinPrice();
            arr[i][7] = st.getTradeAmount();
            arr[i][8] = st.getBuyOnePrice();
            arr[i][9] = st.getSellOnePrice();
        }

        return arr;
    }


    /**
     * {"股票代码", "股票名称", "现价", "持仓", "可卖数量"};
     * @param stPositionList
     * @return
     */
    public static Object[][] positionListToArray(List<StPosition> stPositionList){
        if(CollectionUtils.isEmpty(stPositionList)){
            return null;
        }
        Object[][] arr = new Object[stPositionList.size()][5];
        for(int i=0;i<stPositionList.size();i++){
            StPosition stq = stPositionList.get(i);
            StStockConfig stockConfig = ClientConstants.stStockConfigMapKeyId.get(stq.getStockId());
            StStock stStock = ClientConstants.stStockMap.get(stockConfig.getStockCode());
            arr[i][0] = stockConfig.getStockCode();
            arr[i][1] = stockConfig.getStockName();
            arr[i][2] = stStock.getCurrentPrice();
            arr[i][3] = stq.getAmount();
            arr[i][4] = stq.getMarketAmount();
        }

        return arr;
    }

    /**
     * {"股票代码", "股票名称","报价", "申报数量", "类型", "冻结资金", "状态","报价时间"}
     * @param stQuoteList
     * @return
     */
    public static Object[][] quoteListToArray(List<StQuote> stQuoteList){
        if(CollectionUtils.isEmpty(stQuoteList)){
            return null;
        }
        Object[][] arr = new Object[stQuoteList.size()][9];
        for(int i=0;i<stQuoteList.size();i++){
            StQuote stq = stQuoteList.get(i);
            StStockConfig stock = ClientConstants.stStockConfigMapKeyId.get(stq.getStockId());

            arr[i][0] = stock.getStockCode();
            arr[i][1] = stock.getStockName();
            arr[i][2] = stq.getQuotePrice();
            arr[i][3] = stq.getAmount();
            arr[i][4] = stq.getQuoteType()==1?"买":"卖";
            arr[i][5] = stq.getFrozeMoney()==null?0d:stq.getFrozeMoney();
            //1.托管  2.交易中 3.已成交 4.撤单  5. 结算未成交 6.结算部分股票成交
            if(stq.getStatus()== ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE){
                arr[i][6] = "托管";
            }else if(stq.getStatus()== ApplicationConstants.STOCK_STQUOTE_STATUS_DEALING){
                arr[i][6] = "交易中";
            }else if((stq.getStatus()== ApplicationConstants.STOCK_STQUOTE_STATUS_ALREADYDEAL)){
                arr[i][6] = "已成交";
            }else if((stq.getStatus()== ApplicationConstants.STOCK_STQUOTE_STATUS_REVOKE)){
                arr[i][6] = "撤单";
            }else if((stq.getStatus()== ApplicationConstants.STOCK_STQUOTE_STATUS_NOTDEAL)){
                arr[i][6] = "未成交";
            }else{
                arr[i][6] = "部分成交";
            }
            arr[i][7] = DateUtils.formatLongToStr(stq.getDateTime(), DateUtils.TIME_FORMAT);
            arr[i][8] = stq.getQuoteId();
        }

        return arr;
    }

    /**
     * {"帐户", "股票代码","股票名称","报价", "交易数量", "类型", "交易金额", "佣金","印花税","交易时间"}
     * @param moneyJournals
     * @return
     */
    public static Object[][] journalListToArray(List<StMoneyJournal> moneyJournals){
        if(CollectionUtils.isEmpty(moneyJournals)){
            return null;
        }
        Object[][] arr = new Object[moneyJournals.size()][10];
        for(int i=0;i<moneyJournals.size();i++){
            StMoneyJournal stq = moneyJournals.get(i);
            StStockConfig stock = ClientConstants.stStockConfigMapKeyId.get(stq.getStockId());
            arr[i][0] = stq.getAccountId();
            arr[i][1] = stock.getStockCode();
            arr[i][2] = stock.getStockName();
            arr[i][3] = stq.getQuotePrice().doubleValue();
            arr[i][4] = stq.getAmount();
            arr[i][5] = stq.getDealType()==1?"买":"卖";
            arr[i][6] = stq.getDealMoney()==null?0d:stq.getDealMoney().doubleValue();
            arr[i][7] = stq.getDealFee().doubleValue();
            arr[i][8] = stq.getDealTax()==null?0:stq.getDealTax();
            arr[i][9] = DateUtils.formatLongToStr(stq.getDateTime(), DateUtils.TIME_FORMAT);
        }

        return arr;
    }

    /**
     * {"股票代码", "股票名称","报价", "数量", "类型",  "交易时间"}
     * @param stTradeRecords
     * @return
     */
    public static Object[][] recordListToArray(List<StTradeRecord> stTradeRecords){
        if(CollectionUtils.isEmpty(stTradeRecords)){
            return null;
        }
        Object[][] arr = new Object[stTradeRecords.size()][8];
        for(int i=0;i<stTradeRecords.size();i++){
            StTradeRecord stq = stTradeRecords.get(i);
            StStockConfig stock = ClientConstants.stStockConfigMapKeyId.get(stq.getStockId());
            arr[i][0] = stock.getStockCode();
            arr[i][1] = stock.getStockName();
            arr[i][2] = stq.getQuotePrice().doubleValue();
            arr[i][3] = stq.getAmount();
            if(StringUtils.isNotBlank(stq.getSellerAccountId())){
                arr[i][4] = "买入";
            }else if(StringUtils.isNotBlank(stq.getBuyerAccountId())){
                arr[i][4] = "卖出";
            }
            arr[i][5] = DateUtils.formatLongToStr(stq.getDateTime(),DateUtils.TIME_FORMAT);
            arr[i][6] = "";
        }

        return arr;
    }

}
