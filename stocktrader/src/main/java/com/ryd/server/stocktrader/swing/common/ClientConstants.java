package com.ryd.server.stocktrader.swing.common;

import com.ryd.business.model.StAccount;
import com.ryd.business.model.StPosition;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StStock;
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
}
