package com.ryd.server.stocktrader.swing.service.impl;


import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.model.*;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.frame.*;
import com.ryd.server.stocktrader.swing.service.MessageServiceI;
import com.ryd.server.stocktrader.utils.ParamBuilderDtoUtil;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.javassist.compiler.ast.Stmnt;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端处理与服务端交互数据ServiceImpl
 *
 * Created by Administrator on 2016/4/11.
 */
public class MessageServiceImpl extends MessageServiceI {
    public static ChannelHandlerContext ctx;

    public static void createResponseInfo(DiyNettyMessage.NettyMessage request){
        System.out.println(request.getId() + "---" + request.getStatus());

        switch (request.getId()){
            case ApplicationConstants.NETTYMESSAGE_ID_HEARTBEAT:
                DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
                builder.setId(request.getId());
                builder.setStatus(ApplicationConstants.NETTYMESSAGE_STATUS_RESPONSE_SUCCESS);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_STOCKINFO:
                List<DiyNettyMessage.StockInfo> infoList = request.getStockInfoList();
                ClientConstants.stStockList = new ArrayList<StStock>();
                for(DiyNettyMessage.StockInfo sti : infoList){
                    StStock st = ParamBuilderDtoUtil.getStStock(sti);
                    ClientConstants.stStockList.add(st);
                }
                ClientConstants.stockListToMap();
                MainFrame.instance().open();
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_STOCKPRICEDETAIL:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_LOGIN:
                if(request.getStatus()==1){
                    DiyNettyMessage.AccountInfo ainfo = request.getAccountInfoList().get(0);

                    DiyNettyMessage.NettyMessage.Builder buildera = TestParamBuilderUtil.getAccount(ApplicationConstants.NETTYMESSAGE_ID_MYCAPITAL, ainfo.getAccountId(), 0L, System.currentTimeMillis());
                    MessageServiceImpl.sendMessage(buildera.build());

                    DiyNettyMessage.NettyMessage.Builder builderp = TestParamBuilderUtil.getAccount(ApplicationConstants.NETTYMESSAGE_ID_MYPOSITION,ainfo.getAccountId(),0L,System.currentTimeMillis());
                    MessageServiceImpl.sendMessage(builderp.build());

                    DiyNettyMessage.NettyMessage.Builder builderq = TestParamBuilderUtil.getStockInfoBuilder(ApplicationConstants.NETTYMESSAGE_ID_STOCKINFO, 0, null);
                    MessageServiceImpl.sendMessage(builderq.build());
                }else{
                    JOptionPane.showMessageDialog(null, "帐号信息错误，没有该用户", "提示",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_QUOTE:
                int qrs = request.getStatus();
                if(qrs == 1) {
                    JOptionPane.showMessageDialog(null, "报价成功", "提示",
                            JOptionPane.ERROR_MESSAGE);
                    QuotePriceJDialog.instance().dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "报价失败", "提示",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;
            case ApplicationConstants.NETTYMESSAGE_ID_WITHDRAWORDER:
                int wrs = request.getStatus();
                if(wrs == 1) {
                    JOptionPane.showMessageDialog(null, "撤单成功", "提示",
                            JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "撤单失败", "提示",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYCAPITAL:
                DiyNettyMessage.AccountInfo ainfo = request.getAccountInfoList().get(0);

                StAccount racc = new StAccount();
                racc.setId(ainfo.getAccountId());
                racc.setRealName(ainfo.getRealName());
                racc.setAccountName(ainfo.getAccountName());
                racc.setPassword(ainfo.getPassword());
                racc.setAccountNum(ainfo.getAccountNum());
                racc.setTotalAssets(BigDecimal.valueOf(ainfo.getTotalAssets()));
                racc.setUseMoney(BigDecimal.valueOf(ainfo.getUseMoney()));
                racc.setAccountLevel((short) ainfo.getAccountLevel());
                racc.setMobile(ainfo.getMobile());
                racc.setSex((short) ainfo.getSex());
                racc.setRemark(ainfo.getRemark());

                ClientConstants.stAccount = racc;

                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYPOSITION:
                List<DiyNettyMessage.PositionInfo> positionInfos = request.getPositionInfoList();
                ClientConstants.stPositionList = new ArrayList<StPosition>();
                for(DiyNettyMessage.PositionInfo pi : positionInfos){
                    StPosition stp = ParamBuilderDtoUtil.getStPosition(pi);
                    ClientConstants.stPositionList.add(stp);
                }
                ClientConstants.positionListToMap();
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYQUOTELIST:
                List<DiyNettyMessage.QuoteInfo> quoteInfos = request.getQuoteInfoList();
                ClientConstants.stQuoteList = new ArrayList<StQuote>();
                for(DiyNettyMessage.QuoteInfo qi : quoteInfos){
                    StQuote sqi = ParamBuilderDtoUtil.getStQuote(qi);
                    ClientConstants.stQuoteList.add(sqi);
                }
                ClientConstants.quoteListToMap();

                QuoteListDialog.instance().open();
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYTRADERECORD:
                List<DiyNettyMessage.TradeRecordInfo> tradeRecordInfos = request.getTradeRecordInfoList();
                ClientConstants.stTradeRecordList = new ArrayList<StTradeRecord>();
                for(DiyNettyMessage.TradeRecordInfo tri : tradeRecordInfos){
                    StTradeRecord stri = ParamBuilderDtoUtil.getStTradeRecord(tri);
                    ClientConstants.stTradeRecordList.add(stri);
                }
                ClientConstants.tradeRecordListToMap();

                RecordListDialog.instance().open();
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYMONEYJOURNAL:
                List<DiyNettyMessage.MoneyJournalInfo> moneyJournalInfos = request.getMoneyJournalInfoList();
                ClientConstants.stMoneyJournalList = new ArrayList<StMoneyJournal>();
                for(DiyNettyMessage.MoneyJournalInfo ml : moneyJournalInfos){
                    StMoneyJournal smj = ParamBuilderDtoUtil.getStMoneyJournal(ml);
                    ClientConstants.stMoneyJournalList.add(smj);
                }
                ClientConstants.journalListToMap();

                MoneyJournalListDialog.instance().open();
                break;
            default:
                // 默认状态
                break;
        }
    }

    public static void sendMessage(DiyNettyMessage.NettyMessage obj) {

        ctx.writeAndFlush(obj);
    }
}
