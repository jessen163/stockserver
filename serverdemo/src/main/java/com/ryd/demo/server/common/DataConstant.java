package com.ryd.demo.server.common;

import com.ryd.demo.server.bean.*;
import com.ryd.demo.server.util.ConcurrentSortedLinkedList;
import com.ryd.demo.server.util.SortedLinkedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>标题:数据常量</p>
 * <p>描述:数据常量</p>
 * 包名：com.ryd.stockanalysis.common
 * 创建人：songby
 * 创建时间：2016/3/28 15:57
 */
public class DataConstant {

    //队列-多只股票
    public static ConcurrentHashMap<String,StTradeQueue> stTradeQueueMap = new ConcurrentHashMap<String,StTradeQueue>();
    //帐户
    public static ConcurrentMap<String,StAccount> stAccounts = new ConcurrentHashMap<String,StAccount>();

    // 模拟账户-马甲
    public static List<StAccount> accountList = null;

    static {
        accountList = new ArrayList<StAccount>();
        StAccount ataA = new StAccount("A","A","1",1000000d,2000000d);
        accountList.add(ataA);
    }

    //帐户委托,报价
    public static ConcurrentHashMap<String,Map<String,StQuote>> stAccountQuoteMap = new ConcurrentHashMap<String,Map<String,StQuote>>();
    //持仓
    public static ConcurrentHashMap<String, Map<String,StPosition>> stAccountPositionMap = new ConcurrentHashMap<String, Map<String,StPosition>>();

    //股票列表
    public static ConcurrentMap<String,StStock> stockTable = new ConcurrentHashMap<String,StStock>();
    //买卖家报价
    public static SortedLinkedList<StQuote> sellList = new ConcurrentSortedLinkedList<StQuote>();
    public static SortedLinkedList<StQuote> buyList = new ConcurrentSortedLinkedList<StQuote>();

    //所有报价信息
    public static ConcurrentMap<String,Map> allQuoteTable = new ConcurrentHashMap<String,Map>();
    //交易记录列表
    public static List<StTradeRecord> recordList = new ArrayList<StTradeRecord>();

    //日期列表
    public static Map<String,List<DateSchedule>> dateScheduleMap = new HashMap<String,List<DateSchedule>>();

    //交易费用
    public static Double STOCK_TRADE_AGENT_MONEY = 0d;

    //A、B、C、D、E买卖次数
    public static Integer STQUOTE_A_NUM = 3;
    public static Integer STQUOTE_B_NUM = 3;
    public static Integer STQUOTE_C_NUM = 2;
    public static Integer STQUOTE_D_NUM = 3;
    public static Integer STQUOTE_E_NUM = 2;

    //A、B、C、D、E买卖报价
    public static Double STQUOTE_A_QUOTEPRICE = 10d;
    public static Double STQUOTE_B_QUOTEPRICE = 10.5d;
    public static Double STQUOTE_C_QUOTEPRICE = 9.5d;
    public static Double STQUOTE_D_QUOTEPRICE = 10.5d;
    public static Double STQUOTE_E_QUOTEPRICE = 10d;

    //A、B、C、D、E买卖数量
    public static Integer STQUOTE_A_AMOUNT = 100;
    public static Integer STQUOTE_B_AMOUNT = 100;
    public static Integer STQUOTE_C_AMOUNT = 100;
    public static Integer STQUOTE_D_AMOUNT = 100;
    public static Integer STQUOTE_E_AMOUNT = 100;

    //交易股票
    public static String TRADEING_STOCK_ID = "1";
    public static String TRADEING_STOCK_ID2 = "2";
    public static String TRADEING_STOCK_ID3 = "2";

    //昨日收盘价
    public static Double STQUOTE_PREVIOUS_CLOSEPRICE = 10d;

    //新浪接口URL
    public static String STOCK_SINA_URL = "http://hq.sinajs.cn/list=";
}
