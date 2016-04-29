package com.ryd.server.stocktrader.handle;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.basecommon.util.SpringUtils;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.dto.*;
import com.ryd.business.model.*;
import com.ryd.business.service.*;
import com.ryd.business.service.impl.StAccountServiceImpl;
import com.ryd.server.stocktrader.utils.ParamBuilderUtil;
import io.netty.channel.ChannelHandlerContext;
import scala.Int;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端消息处理
 *
 * Created by Administrator on 2016/4/22.
 */
public class MessageHandle {
    public static ChannelHandlerContext ctx;

    private static StAccountService stAccountService = null;
    private static StQuoteService stQuoteService = null;
    private static StMoneyJournalService stMoneyJournalService = null;
    private static StTradeRecordService stTradeRecordService = null;
    private static StPositionService stPositionService = null;
    private static StStockService stStockService = null;
    static {
        stAccountService = SpringUtils.getBean(StAccountService.class, "stAccountServiceImpl");
        stQuoteService = SpringUtils.getBean(StQuoteService.class, "stQuoteServiceImpl");
        stMoneyJournalService = SpringUtils.getBean(StMoneyJournalService.class, "stMoneyJournalServiceImpl");
        stTradeRecordService = SpringUtils.getBean(StTradeRecordService.class, "stTradeRecordServiceImpl");
        stPositionService = SpringUtils.getBean(StPositionService.class, "stPositionServiceImpl");
        stStockService = SpringUtils.getBean(StStockService.class, "stStockServiceImpl");
    }

    /**
     * 处理客户端消息
     * @param request
     * @return
     */
    public static DiyNettyMessage.NettyMessage handleClientRequestInfo(DiyNettyMessage.NettyMessage request) throws Exception {
        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
        builder.setId(request.getId());
        builder.setKey("0");
        switch (request.getId()){
            case ApplicationConstants.NETTYMESSAGE_ID_HEARTBEAT:
                builder.setStatus(ApplicationConstants.NETTYMESSAGE_STATUS_RESPONSE_SUCCESS);
                StAccount account = stAccountService.findStAccount("test", "1234");
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_STOCKINFO:
                SearchStockDTO searchStockDTO = new SearchStockDTO();
                searchStockDTO.setBoardType(request.getType());
                List<StStock> stStocks = stStockService.findStockList(searchStockDTO);

                for(StStock stc : stStocks) {
                    builder.addStockInfo(ParamBuilderUtil.getStockInfoBuilder(stc));
                }

                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_STOCKPRICEDETAIL:
                DiyNettyMessage.StockInfo sinfo = request.getStockInfoList().get(0);
                SearchStockDTO searchStock = new SearchStockDTO();
                searchStock.setStockId(sinfo.getId());
                StStock st = stStockService.findStockListByStock(searchStock);

                builder.addStockInfo(ParamBuilderUtil.getStockInfoBuilder(st));
                builder.addStockPriceInfo(ParamBuilderUtil.getStockPriceInfoBuilder(st));

                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_LOGIN:
                DiyNettyMessage.AccountInfo acc = request.getAccountInfoList().get(0);
                account = stAccountService.findStAccount(acc.getAccountNum(), acc.getPassword());

                if(account !=null ){
                    builder.setStatus(1);
                    builder.addAccountInfo(DiyNettyMessage.AccountInfo.newBuilder().setAccountId(account.getId()).setAccountNum(account.getAccountNum()));
                }else{
                    builder.setStatus(2);
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_QUOTE:
                DiyNettyMessage.QuoteInfo quote = request.getQuoteInfoList().get(0);

                StQuote q = new StQuote();
                q.setQuoteId(UUIDUtils.uuidTrimLine());
                q.setStockId(quote.getStockId());
                q.setAccountId(request.getAccountId());
                q.setQuotePrice(BigDecimal.valueOf(quote.getStockPrice()));
                q.setQuoteType((short) quote.getQuoteType());
                q.setAmount(Long.valueOf(quote.getAmount()));

                int rs = stQuoteService.saveQuoteList(q);
                if(rs >= 0 ){
                    builder.setStatus(1);
                }else{
                    builder.setStatus(2);
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_WITHDRAWORDER:
                DiyNettyMessage.QuoteInfo revokeQuote = request.getQuoteInfoList().get(0);
                StQuote searchQuote = new StQuote();
                searchQuote.setQuoteId(revokeQuote.getQuoteId());
                StQuote rQuote = stQuoteService.findQuoteById(searchQuote);
                int qrs = stQuoteService.updateWithDrawQuote(rQuote);
                if(qrs >= 0 ){
                    builder.setStatus(1);
                }else{
                    builder.setStatus(2);
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYCAPITAL:
                StAccount stAcc = stAccountService.getStAccountById(request.getAccountId());
                builder.addAccountInfo(ParamBuilderUtil.getAccountInfoBuilder(stAcc));
                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYPOSITION:
                SearchPositionDTO searchPositionDTO = new SearchPositionDTO();
                searchPositionDTO.setAccountId(request.getAccountId());
                searchPositionDTO.setOffset(request.getOffset());
                searchPositionDTO.setLimit(request.getSize());
                List<StPosition> positions = stPositionService.findPositionList(searchPositionDTO);

                for(StPosition sp : positions) {
                    builder.addPositionInfo(ParamBuilderUtil.getPositionInfoBuilder(sp));
                }
                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYQUOTELIST:
                SearchQuoteDTO searchQuoteDTO = new SearchQuoteDTO();
                searchQuoteDTO.setAccountId(request.getAccountId());
                String start = DateUtils.formatLongToStr(request.getStartTime(), DateUtils.TIME_FORMAT);
                String end = DateUtils.formatLongToStr(request.getEndTime(), DateUtils.TIME_FORMAT);
                searchQuoteDTO.setQuoteStartDate(DateUtils.formatStrToDate(start,DateUtils.TIME_FORMAT));
                searchQuoteDTO.setQuoteEndDate(DateUtils.formatStrToDate(end,DateUtils.TIME_FORMAT));
                searchQuoteDTO.setOffset(request.getOffset());
                searchQuoteDTO.setLimit(request.getSize());
                List<StQuote> quotes1 = stQuoteService.findQuoteList(searchQuoteDTO);

                for(StQuote q1:quotes1){
                    builder.addQuoteInfo(ParamBuilderUtil.getQuoteInfoBuilder(q1));
                }
                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYTRADERECORD:
                SearchTradeRecordDTO searchTradeRecordDTO = new SearchTradeRecordDTO();
                searchTradeRecordDTO.setAccountId(request.getAccountId());
                String startr = DateUtils.formatLongToStr(request.getStartTime(), DateUtils.TIME_FORMAT);
                String endr = DateUtils.formatLongToStr(request.getEndTime(), DateUtils.TIME_FORMAT);
                searchTradeRecordDTO.setStartDate(DateUtils.formatStrToDate(startr, DateUtils.TIME_FORMAT));
                searchTradeRecordDTO.setEndDate(DateUtils.formatStrToDate(endr, DateUtils.TIME_FORMAT));
                searchTradeRecordDTO.setOffset(request.getOffset());
                searchTradeRecordDTO.setLimit(request.getSize());
                List<StTradeRecord> records = stTradeRecordService.findTradeRecordList(searchTradeRecordDTO);

                for(StTradeRecord r1:records){

                    builder.addTradeRecordInfo(ParamBuilderUtil.getTradeRecordInfoBuilder(r1,request.getAccountId()));
                }
                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYMONEYJOURNAL:
                SearchMoneyJournalDTO searchMoneyJournalDTO = new SearchMoneyJournalDTO();
                searchMoneyJournalDTO.setAccountId(request.getAccountId());
                String startm = DateUtils.formatLongToStr(request.getStartTime(), DateUtils.TIME_FORMAT);
                String endm = DateUtils.formatLongToStr(request.getEndTime(), DateUtils.TIME_FORMAT);
                searchMoneyJournalDTO.setStartDate(DateUtils.formatStrToDate(startm,DateUtils.TIME_FORMAT));
                searchMoneyJournalDTO.setEndDate(DateUtils.formatStrToDate(endm, DateUtils.TIME_FORMAT));
                searchMoneyJournalDTO.setOffset(request.getOffset());
                searchMoneyJournalDTO.setLimit(request.getSize());
                List<StMoneyJournal> journals = stMoneyJournalService.findMoneyJournalList(searchMoneyJournalDTO);

                for(StMoneyJournal journal : journals) {

                    builder.addMoneyJournalInfo(ParamBuilderUtil.getMoneyJournalInfoBuilder(journal));
                }
                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_ACCOUNTLIST:
                SearchAccountDTO searchAccountDTO = new SearchAccountDTO();
                searchAccountDTO.setAccountId(request.getAccountId());
                String starta = DateUtils.formatLongToStr(request.getStartTime(), DateUtils.TIME_FORMAT);
                String enda = DateUtils.formatLongToStr(request.getEndTime(), DateUtils.TIME_FORMAT);
                searchAccountDTO.setStartDate(DateUtils.formatStrToDate(starta,DateUtils.TIME_FORMAT));
                searchAccountDTO.setEndDate(DateUtils.formatStrToDate(enda, DateUtils.TIME_FORMAT));
                searchAccountDTO.setOffset(request.getOffset());
                searchAccountDTO.setLimit(request.getSize());
                List<StAccount> alist =  stAccountService.findStAccountList(searchAccountDTO);

                for(StAccount asc:alist){
                    builder.addAccountInfo(ParamBuilderUtil.getAccountInfoBuilder(asc));
                }
                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_ACCOUNTUPDATE:
                DiyNettyMessage.AccountInfo accu = request.getAccountInfoList().get(0);
                StAccount sacc = new StAccount();
                sacc.setId(accu.getAccountId());
                sacc.setRealName(accu.getRealName());
                sacc.setAccountName(accu.getAccountName());
                sacc.setPassword(accu.getPassword());
                sacc.setAccountNum(accu.getAccountNum());
                sacc.setTotalAssets(BigDecimal.valueOf(accu.getTotalAssets()));
                sacc.setUseMoney(BigDecimal.valueOf(accu.getUseMoney()));
                sacc.setAccountLevel((short) accu.getAccountLevel());
                sacc.setMobile(accu.getMobile());
                sacc.setSex((short) accu.getSex());
                sacc.setRemark(accu.getRemark());

                boolean sars = stAccountService.updateStAccount(sacc);
                if(sars){
                    builder.setStatus(1);
                    builder.addAccountInfo(DiyNettyMessage.AccountInfo.newBuilder().setAccountId(sacc.getId()));
                }else{
                    builder.setStatus(2);
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_SINGLESTOCKTRADEQUEUE:
                DiyNettyMessage.StockInfo ssinfo = request.getStockInfoList().get(0);
                SearchQuoteDTO ssSearchQuoteDTO = new SearchQuoteDTO();
                ssSearchQuoteDTO.setStockCode(ssinfo.getStockCode());
                ssSearchQuoteDTO.setQuoteType(request.getType());

                List<StQuote> ssQuoteList = stQuoteService.findQuoteQueueByStock(ssSearchQuoteDTO);

                for(StQuote ssq:ssQuoteList){
                    builder.addQuoteInfo(ParamBuilderUtil.getQuoteInfoBuilder(ssq));
                }

                builder.setStatus(1);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_SINGLESTOCKTRADERECORD:
                DiyNettyMessage.StockInfo sqinfo = request.getStockInfoList().get(0);
                SearchTradeRecordDTO sqSearchTradeRecordDTO = new SearchTradeRecordDTO();

                List<StTradeRecord> sqRecordList = stTradeRecordService.findTradeRecordListByStock(sqSearchTradeRecordDTO);

                for(StTradeRecord sqr:sqRecordList){
                    builder.addTradeRecordInfo(ParamBuilderUtil.getTradeRecordInfoBuilder(sqr,""));
                }
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_REGISTER:
                DiyNettyMessage.AccountInfo ainfo = request.getAccountInfoList().get(0);

                StAccount racc = new StAccount();
                racc.setId(UUIDUtils.uuidTrimLine());
                racc.setRealName(ainfo.getRealName());
                racc.setAccountName(ainfo.getAccountName());
                racc.setPassword(ainfo.getPassword());
                racc.setAccountNum(ainfo.getAccountNum());
                racc.setTotalAssets(BigDecimal.valueOf(ainfo.getTotalAssets()));
                racc.setUseMoney(BigDecimal.valueOf(ainfo.getUseMoney()));
                racc.setAccountLevel((short) ainfo.getAccountLevel());
                racc.setMobile(ainfo.getMobile());
                racc.setSex((short)ainfo.getSex());
                racc.setRemark(ainfo.getRemark());

                boolean rars = stAccountService.addStAccount(racc);
                if(rars){
                    builder.setStatus(1);
                    builder.addAccountInfo(DiyNettyMessage.AccountInfo.newBuilder().setAccountId(racc.getId()));
                }else{
                    builder.setStatus(2);
                }
                break;
            default:
                // 默认状态
                break;
        }

        return builder.build();
    }

    /**
     * 发送消息给客户端
     * @param message
     */
    public static void sendMessage(DiyNettyMessage.NettyMessage message) {
        ctx.writeAndFlush(message);
    }
}