package com.ryd.demo.swing.common;

import com.ryd.demo.server.bean.*;
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

    //1、登陆 2、股票行情 3、我的报价信息 4、报价 5、撤单 6、持仓信息
    public static final Integer CLIENT_LOGIN = 1;
    public static final Integer STSTOCK_LIST = 2;
    public static final Integer STQUOTE_PRICE_LIST = 3;
    public static final Integer STQUOTE_PRICE = 4;
    public static final Integer STQUOTE_RECALL = 5;
    public static final Integer STSTOCK_POSITION = 6;

//    static {
//        stStockList = new ArrayList<StStock>();
//        stAccount = new StAccount();
//        stPositionList = new ArrayList<StPosition>();
//        stQuoteList = new ArrayList<StQuote>();
//
//        StStock stStock = new StStock("601318","中国平安","601318","sh");
//        stStock.setCurrentPrice(31);
//        stStock.setOpenPrice(29);
//        stStock.setBfclosePrice(39.5);
//        stStock.setMaxPrice(32);
//        stStock.setMinPrice(28);
//        stStock.setTradeAmount(100001);
//
//        StStock stStock2 = new StStock("000776","广发证券","000776", "sz");
//
//        stStock2.setCurrentPrice(3.2);
//        stStock2.setOpenPrice(2.9);
//        stStock2.setBfclosePrice(3.95);
//        stStock2.setMaxPrice(3.2);
//        stStock2.setMinPrice(2.8);
//        stStock2.setTradeAmount(103301);
//
//        stStockList.add(stStock);
//        stStockList.add(stStock2);
//
//        stAccount = new StAccount("A","A","A",10000d,1000000d);
//        //用户A持仓
//        StPosition ata5Pos = new StPosition();
//        ata5Pos.setPositionId(UUID.randomUUID().toString());
//        ata5Pos.setAccountId(stAccount.getAccountId());
//        ata5Pos.setStockId(stStock.getStockId());
//        ata5Pos.setAmount(1000);
//        ata5Pos.setStatus(1);
//
//        //用户A持仓
//        StPosition ata4Pos = new StPosition();
//        ata4Pos.setPositionId(UUID.randomUUID().toString());
//        ata4Pos.setAccountId(stAccount.getAccountId());
//        ata4Pos.setStockId(stStock2.getStockId());
//        ata4Pos.setAmount(120);
//        ata4Pos.setStatus(1);
//
//        stPositionList.add(ata5Pos);
//        stPositionList.add(ata4Pos);
//
//        stockListToMap();
//        positionListToMap();
//    }


    public static void stockListToMap() {
        if (CollectionUtils.isNotEmpty(stStockList)) {
            stStockMap.clear();
            for (StStock st : stStockList) {
                stStockMap.put(st.getStockId(), st);
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
