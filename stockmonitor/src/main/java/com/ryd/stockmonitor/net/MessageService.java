package com.ryd.stockmonitor.net;

import com.ryd.demo.server.bean.StQuote;
import com.ryd.demo.server.bean.StStock;
import com.ryd.demo.server.bean.StTradeRecord;
import com.ryd.protocol.NettyMessage;
import com.ryd.stockmonitor.Constant;
import com.ryd.stockmonitor.util.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import com.ryd.demo.server.bean.StTradeQueue;

import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 消息服务-用于接收服务器端监控信息、通知前端
 * Created by Administrator on 2016/4/14.
 */
public class MessageService {

//    private static Logger logger = Logger.getLogger(MessageService.class);
    public static ChannelHandlerContext ctx;
    public static Session session;

    public static synchronized void doMsgForShunt(Object msg) {
        NettyMessage rsmsg = (NettyMessage)msg;
        int msgType = rsmsg.getMsgType();
        try {
            switch (msgType) {
                case 2:
                    List<StStock> stockList = (List<StStock>)rsmsg.getMsgObj();
                    session.getBasicRemote().sendText(JsonUtil.toJSon(stockList));
                    for (StStock stock : stockList) {
                        Constant.stockMap.put(stock.getStockId(), stock.getStockName());
                    }

                    break;
                case 7 :
                    // 股票单条买/卖
                    List<StQuote> quoteList = (List<StQuote>) rsmsg.getMsgObj();
                    session.getBasicRemote().sendText(JsonUtil.toJSon(quoteList));

                    break;
                case 8 :
                    // 最新的10条报价-买/卖
//                    StTradeQueue stTradeQueue = (StTradeQueue) rsmsg.getMsgObj();
//                    session.getBasicRemote().sendText(JsonUtil.toJSon(stTradeQueue));
//                    Map<String, Object> stockDetailMap = (Map)rsmsg.getMsgObj();
//                    session.getBasicRemote().sendText(JsonUtil.toJSon(stockDetailMap));
                    Map<String, Object> stockDetailMap = (Map<String, Object>)rsmsg.getMsgObj();
                    session.getBasicRemote().sendText(JsonUtil.toJSon(stockDetailMap));

                    break;
                case 9 :
                    // 最新的交易记录
//                    List<StTradeRecord> recordList = (List<StTradeRecord>)rsmsg.getMsgObj();
//                    session.getBasicRemote().sendText(JsonUtil.toJSon(recordList));

                    break;
                case 10 :
    //                logger.info("获取单只股票的最近五次报价");
                    stockList = (LinkedList<StStock>)rsmsg.getMsgObj();
                    session.getBasicRemote().sendText(JsonUtil.toJSon(stockList));

                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Object obj) {
        ctx.writeAndFlush(obj);
    }
}
