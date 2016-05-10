package com.ryd.server.stocktrader.swing.common;

import com.ryd.business.model.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 客户端全局常量/变量表
 * Created by Administrator on 2016/4/11.
 */
public class ClientConstants {
    // 股票信息
    public static List<StStock> stStockList = null;
    // 股票信息
    public static Map<String, StStock> stStockMap = new HashMap<String, StStock>();
    // 股票信息
    public static List<StStockConfig> stStockConfigList = null;
    // 股票信息
    public static Map<String, StStockConfig> stStockConfigMapKeyId = new HashMap<String, StStockConfig>();

    public static Map<String, StStockConfig> stStockConfigMapKeyCode = new HashMap<String, StStockConfig>();

    // 账户信息
    public static StAccount stAccount = null;
    // 仓位信息
    public static List<StPosition> stPositionList = null;
    // 仓位信息
    public static Map<String, StPosition> stPositionMap = new HashMap<String, StPosition>();

    // 仓位报价信息
    public static List<StQuote> stQuoteList = null;
    // 仓位报价信息
    public static Map<String, StQuote> stQuoteMap = new HashMap<String, StQuote>();

    // 交易记录信息
    public static List<StTradeRecord> stTradeRecordList = null;
    // 交易记录信息
    public static Map<String, StTradeRecord> stTradeRecordMap = new HashMap<String, StTradeRecord>();

    // 资金流水信息
    public static List<StMoneyJournal> stMoneyJournalList = null;
    // 资金流水信息
    public static Map<String, StMoneyJournal> stMoneyJournalMap = new HashMap<String, StMoneyJournal>();


    // 监控端交易记录信息
    public static List<StTradeRecord> monitorTradeRecordList = null;
    // 监控端买入报价信息
    public static List<StQuote> monitorQuoteBuyList = null;
    // 监控端卖出报价信息
    public static List<StQuote> monitorQuoteSellList = null;
    //监控端股票信息
    public static List<StStock> monitorStockInfoList = null;
    public static Map<String, StStock> monitorStockInfoMap = new HashMap<String, StStock>();

    public static void stockListToMap() {
        if (CollectionUtils.isNotEmpty(stStockList)) {
            stStockMap.clear();
            for (StStock st : stStockList) {
                stStockMap.put(st.getStockCode(), st);
            }
        }
    }

    public static void positionListToMap(){
        if(CollectionUtils.isNotEmpty(stPositionList)) {
            stPositionMap.clear();
            for (StPosition sp : stPositionList) {
                stPositionMap.put(sp.getStockId(),sp);
            }
        }
    }


    public static void quoteListToMap(){
        if(CollectionUtils.isNotEmpty(stQuoteList)) {
            stQuoteMap.clear();
            for (StQuote sq : stQuoteList) {
                stQuoteMap.put(sq.getQuoteId(),sq);
            }
        }
    }

    public static void tradeRecordListToMap(){
        if(CollectionUtils.isNotEmpty(stTradeRecordList)) {
            stTradeRecordMap.clear();
            for (StTradeRecord rt : stTradeRecordList) {
                stTradeRecordMap.put(rt.getRecordId(),rt);
            }
        }
    }

    public static void journalListToMap(){
        if(CollectionUtils.isNotEmpty(stMoneyJournalList)) {
            stMoneyJournalMap.clear();
            for (StMoneyJournal mj : stMoneyJournalList) {
                stMoneyJournalMap.put(mj.getRecordId(),mj);
            }
        }
    }

    public static void stockConfigListToMap(){
        if(CollectionUtils.isNotEmpty(stStockConfigList)) {
            stStockConfigMapKeyId.clear();
            stStockConfigMapKeyCode.clear();
            for (StStockConfig cj : stStockConfigList) {
                stStockConfigMapKeyId.put(cj.getId(),cj);
                stStockConfigMapKeyCode.put(cj.getStockCode(),cj);
            }
        }
    }

    public static void monitorStockInfoListToMap(){
        if(CollectionUtils.isNotEmpty(monitorStockInfoList)) {
            monitorStockInfoMap.clear();
            for (StStock ss : monitorStockInfoList) {
                monitorStockInfoMap.put(ss.getStockCode(),ss);
            }
        }
    }
}
