package com.ryd.demo.server.net;

import com.ryd.demo.server.bean.*;
import com.ryd.demo.server.common.DataConstant;
import com.ryd.protocol.NettyMessage;
import com.ryd.demo.server.service.StAccountServiceI;
import com.ryd.demo.server.service.StStockServiceI;
import com.ryd.demo.server.service.StockAnalysisServiceI;
import com.ryd.demo.server.service.impl.StAccountServiceImpl;
import com.ryd.demo.server.service.impl.StStockServiceImpl;
import com.ryd.demo.server.service.impl.StockAnalysisServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 处理客户端任务
 * Created by Administrator on 2016/4/6.
 */
public class StockServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(StockServerHandler.class);

    StockAnalysisServiceI stockAnalysisServiceI = new StockAnalysisServiceImpl();
    StAccountServiceI stAccountServiceI = new StAccountServiceImpl();
    StStockServiceI stStockServiceI = new StStockServiceImpl();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof NettyMessage) {
            NettyMessage nettyMessage = (NettyMessage) msg;
            Integer msgType = nettyMessage.getMsgType();
            boolean flag = false;
            StQuote stQuote = null;
            StAccount account = null;
            String stockId = null;
            switch (msgType) {
                case 1 :
                    // 登陆
                    account = (StAccount) nettyMessage.getMsgObj();
                    account = stAccountServiceI.findStAccountByParams(account);
                    logger.info("登陆信息：" + (account == null ? "登陆失败":account.getAccountNumber()));
                    nettyMessage.setMsgObj(account);
                    logger.info("登陆信息：" + account == null ? "登陆失败" : "登陆成功");
                    break;
                case 2 :
                    // 获取股票信息-行情
                    logger.info("获取股票信息-行情");
                    List<StStock> stStockList = stStockServiceI.findStockList();
                    nettyMessage.setMsgObj(stStockList);
                    logger.info("股票信息-行情:" + stStockList.toString());
                    break;
                case 3 :
                    // 我的报价信息
                    account = (StAccount) nettyMessage.getMsgObj();
                    logger.info("获取我的报价信息："+account.getAccountId());
                    List<StQuote> quoteList = new ArrayList<StQuote>();
                    Map<String,StQuote> stQuoteMap= DataConstant.stAccountQuoteMap.get(account.getAccountId());
                    if (stQuoteMap!=null) {
                        for (String k : stQuoteMap.keySet()) {
                            quoteList.add(stQuoteMap.get(k));
                        }
                    }
                    nettyMessage.setMsgObj(quoteList);
                    logger.info("我的报价信息：" + quoteList.toString());
                    break;
                case 4 :
                    // 报价
                    stQuote = (StQuote) nettyMessage.getMsgObj();
                    logger.info("报价："+stQuote.toString());
                    flag = stockAnalysisServiceI.quotePrice(stQuote);
                    nettyMessage.setMsgObj(flag);
                    logger.info("报价状态："+ (flag ? "报价成功" : "报价失败"));
                    break;
                case 5 :
                    // 撤单
                    stQuote = (StQuote) nettyMessage.getMsgObj();
                    logger.info("撤单："+stQuote.getQuoteId());
                    flag = stockAnalysisServiceI.cancelStQuote(stQuote);
                    nettyMessage.setMsgObj(flag);
                    logger.info("撤单状态："+ (flag ? "报价成功" : "报价失败"));
                    break;
                case 6 :
                    // 持仓信息
                    account = (StAccount) nettyMessage.getMsgObj();
                    logger.info("获取我的持仓信息："+account.getAccountId());
                    Map<String,StPosition> stPositionMap= DataConstant.stAccountPositionMap.get(account.getAccountId());
                    List<StPosition> positionList = new ArrayList<StPosition>();
                    if (stPositionMap!=null) {
                        for (String k : stPositionMap.keySet()) {
                            positionList.add(stPositionMap.get(k));
                        }
                    }
                    nettyMessage.setMsgObj(positionList);
                    logger.info("我的持仓信息：" + positionList.toString());
                    break;
                case 7 :
                    // 客户报价单条报价信息、买-卖---TODO 时间倒序
                    logger.info("客户报价单条报价信息");
                    quoteList = new ArrayList<StQuote>();
                    if (DataConstant.newQuoteList!=null) {
                            for (String key : DataConstant.newQuoteList.keySet()) {
                                StQuote stQuoteNew = DataConstant.newQuoteList.get(key);
                                quoteList.add(stQuoteNew);
                                if (quoteList!=null&&quoteList.size()==20) break;
                            }
                    }
                    nettyMessage.setMsgObj(quoteList);
                    logger.info("客户报价单条报价信息：" + quoteList.toString());
                    break;
                case 8 :
                    // 最新的10条报价-买/卖
                    logger.info("最新的10条报价-买/卖--交易记录");
                    stockId = nettyMessage.getMsgObj().toString();
                    nettyMessage.setMsgObj(DataConstant.stTradeQueueMap.get(stockId));
                    StTradeQueue tradeQueue = DataConstant.stTradeQueueMap.get(stockId);
                    Map<String, Object> stockDetailMap =new HashMap<String, Object>();
                    // 买、卖队列
                    List<StQuote> buyQuote = new ArrayList<StQuote>();
                    List<StQuote> sellQuote = new ArrayList<StQuote>();
                    if (tradeQueue==null||tradeQueue.buyList.isEmpty()) {
                        tradeQueue = new StTradeQueue();
                        ConcurrentSkipListMap<Long, StQuote> list = new ConcurrentSkipListMap<Long, StQuote>();
                        Collection<StQuote> quotes = list.values();
                        for (StQuote q: quotes) {
                            buyQuote.add(q);
                        }
                    } else {
                        ConcurrentSkipListMap<Long, StQuote> list = tradeQueue.buyList;
                        Collection<StQuote> quotes = list.values();
                        for (StQuote q: quotes) {
                            buyQuote.add(q);
                        }
                        list = tradeQueue.sellList;
                        quotes = list.values();
                        for (StQuote q: quotes) {
                            sellQuote.add(q);
                        }
                    }
                    if (buyQuote.isEmpty()||sellQuote.isEmpty()) {
                        StQuote quote = new StQuote();
                        quote.setAccountId("A");
                        quote.setQuotePrice(20d);
                        buyQuote.add(quote);
                        sellQuote.add(quote);
                    }
                    // 买队列
                    stockDetailMap.put("buyqueue", buyQuote);
                    // 卖队列
                    stockDetailMap.put("sellqueue", sellQuote);

                    List<StTradeRecord> recordList = new ArrayList<StTradeRecord>();
                    if (recordList!=null&&recordList.size()>0) {
                        for (int i = recordList.size()-1; i>=0; i--) {
                            StTradeRecord record = recordList.get(i);
                            if (record.getStockId().equals(stockId)) {
                                recordList.add(record);
                                if (recordList.size()==10) break;
                            }
                        }
                    }
//                    if (recordList.isEmpty()) {
//                        StTradeRecord record = new StTradeRecord();
//                        record.setSellerAccountNumber("001");
//                        record.setBuyerAccountNumber("002");
//                        record.setAmount(200);
//                        record.setDateTime(System.currentTimeMillis());
//                        record.setQuotePrice(20.0);
//                        record.setStockCode("100010");
//                        record.setStockId("100010");
//                        recordList.add(record);
//                    }

                    // 交易队列
                    stockDetailMap.put("traderecodeList", recordList);
                    nettyMessage.setMsgObj(stockDetailMap);

                    logger.info("最新的10条报价-买/卖--交易记录：" + stockDetailMap);
                    break;
//                case 9 :
                    // 最新的交易记录---TODO 废弃
//                    logger.info("最新的交易记录");
//                    stockId = (Integer)nettyMessage.getMsgObj();
//                    List<StTradeRecord> recordList = new ArrayList<StTradeRecord>();
//                    if (recordList!=null&&recordList.size()>0) {
//                        for (int i = recordList.size()-1; i>=0; i--) {
//                            StTradeRecord record = recordList.get(i);
//                            if (record.getStockId().equals(stockId)) {
//                                recordList.add(record);
//                                if (recordList.size()==10) break;
//                            }
//                        }
//                    }
//                    nettyMessage.setMsgObj(recordList);
//                    logger.info("最新的10条最新的交易记录：" + recordList);

//                    break;
                case 10 :
                    logger.info("获取单只股票的最近五次报价");
                    stockId = nettyMessage.getMsgObj().toString();
                    LinkedList<StStock> stockList = DataConstant.stockPriceList.get(stockId);
//                    if (stockList!=null && stockList.size()<=1) {
//                        stockList.add(stockList.get(0));
//                        stockList.add(stockList.get(0));
//                        stockList.add(stockList.get(0));
//                        stockList.add(stockList.get(0));
//                    }

                    nettyMessage.setMsgObj(stockList);
                    logger.info("获取单只股票的最近五次报价：" + stockList);
                    break;
            }
            ctx.writeAndFlush(nettyMessage);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
//        cause.printStackTrace();
        logger.info("客户端断开链接-----------------------------------");
        ctx.close();
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Long, StQuote> list = new ConcurrentSkipListMap<Long, StQuote>();
        StQuote quote = new StQuote();
        quote.setAccountId("A");
        quote.setQuotePrice(20d);
        list.put(Long.valueOf(1L), quote);

        Collection<StQuote> quotes = list.values();

        for (StQuote s: quotes) {
            System.out.println(s.getAccountId()+":"+s.getQuotePrice());
        }
    }
}
