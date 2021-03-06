package com.ryd.business.service.impl;

import com.ryd.basecommon.util.*;
import com.ryd.business.dao.StTradeRecordDao;
import com.ryd.business.dto.SearchTradeRecordDTO;
import com.ryd.business.exception.TradeBusinessException;
import com.ryd.business.model.StMoneyJournal;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StTradeRecord;
import com.ryd.business.service.*;
import com.ryd.business.service.thread.TradingMainThread;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.business.service.util.Utils;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.service.StSystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;


/**
 * <p>标题:交易记录业务实现类</p>
 * <p>描述:交易记录业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
@Service
public class StTradeRecordServiceImpl implements StTradeRecordService {
    @Autowired
    private StTradeRecordDao stTradeRecordDao;
    @Autowired
    private ICacheService iCacheService;
    @Autowired
    private StQuoteService stQuoteService;
    @Autowired
    private StPositionService stPositionService;
    @Autowired
    private StSystemParamService stSystemParamService;
    @Autowired
    private StAccountService stAccountService;
    @Autowired
    private StMoneyJournalService stMoneyJournalService;

    @Override
    public boolean saveTradeRecordBatch(List<StTradeRecord> tradeRecordList) {
        return false;
    }

    @Override
    public boolean addTradeRecord(StTradeRecord record) throws Exception{
        boolean rs = stTradeRecordDao.add(record) > 0;
        if(!rs){
            throw new TradeBusinessException("交易记录添加失败");
        }
        return rs;
    }

    @Override
    public void updateStockTrading() {
        // 启动交易线程
        // 检查交易时间
        // 从队列获取买卖报价
        // 匹配买卖报价成功，记录交易日志，更新买、卖双方账户信息，更新仓位信息
        // 记录资金流水，更新双方报价信息
        // 操作过程中（每分钟）停止交易，获取股票信息（调用股票更新方法），生成马甲订单，完成后重新启动交易方法
//        ExecutorService tradingservice = Executors.newFixedThreadPool(2);
        ThreadPoolExecutor tradingservice = null;
        try {
            BlockingQueue<Runnable> stockQueue = new LinkedBlockingQueue<Runnable>();
            tradingservice = new ThreadPoolExecutor(10, 20, 1, TimeUnit.DAYS, stockQueue);

            tradingservice.execute(new TradingMainThread(tradingservice, stQuoteService, this));
        } catch (Exception e){
            e.printStackTrace();
            tradingservice.shutdownNow();
        }
    }

    @Override
    public void updateTradeSettling(StQuote buyQuote, StQuote sellQuote) throws Exception{
        //股票交易数量
        long tradeStockAmount = 0;
        System.out.println(BusinessConstants.stTradeQueueMap);
        //买少卖多
        if (buyQuote.getCurrentAmount().longValue() < sellQuote.getCurrentAmount().longValue()) {
            //股票交易数量为买家购买数量
            tradeStockAmount = buyQuote.getCurrentAmount();

            //处理交易
            this.saveTrading(buyQuote, sellQuote, tradeStockAmount);

            //交易成功，交易卖家持仓减少
            sellQuote.setCurrentAmount(sellQuote.getCurrentAmount() - tradeStockAmount);

            //买家移出队列
            boolean drsb = stQuoteService.deleteQuoteFromQueue(buyQuote);
            if(!drsb){
                throw new TradeBusinessException("交易买家移出队列失败");
            }

            //修改买家卖家报价状态
            buyQuote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_ALREADYDEAL);
            sellQuote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_DEALING);
            if(buyQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL) {
//                iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(buyQuote));
            }else if(buyQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_REAL){
                stQuoteService.updateQuote(buyQuote);
            }
            if(sellQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL) {
//                iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(sellQuote));
            }else if(sellQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_REAL){
                stQuoteService.updateQuote(sellQuote);
            }
        } else if (buyQuote.getCurrentAmount().longValue() == sellQuote.getCurrentAmount().longValue()) {
            //买卖相等
            tradeStockAmount = buyQuote.getCurrentAmount();
            //处理交易
            this.saveTrading(buyQuote, sellQuote, tradeStockAmount);

            //买家卖家移出队列
            boolean desb = stQuoteService.deleteQuoteFromQueue(buyQuote);
            boolean dess = stQuoteService.deleteQuoteFromQueue(sellQuote);

            if((!desb)||(!dess)){
                throw new TradeBusinessException("交易买家或卖家移出队列失败");
            }

            //修改买家卖家报价状态
            buyQuote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_ALREADYDEAL);
            sellQuote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_ALREADYDEAL);
            if(buyQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL) {
//                iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(buyQuote));
            }else if(buyQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_REAL){
                stQuoteService.updateQuote(buyQuote);
            }
            if(sellQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL) {
//                iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(sellQuote));
            }else if(sellQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_REAL){
                stQuoteService.updateQuote(sellQuote);
            }
        }else if(buyQuote.getCurrentAmount().longValue() > sellQuote.getCurrentAmount().longValue()){
            //买多卖少
            //股票交易数量为卖家卖掉数量
            tradeStockAmount = sellQuote.getCurrentAmount();
            //处理交易
            this.saveTrading(buyQuote, sellQuote, tradeStockAmount);

            //买家报价剩余股票数量
            long remainAmount = buyQuote.getCurrentAmount()-tradeStockAmount;

            //佣金比例
            String commissionPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_COMMINSSION_PERCENT);
            //最小佣金值
            String minCommissionFee = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_COMMISIONFEE_MIN);

            BigDecimal[] rsArr = Utils.calculate(buyQuote.getQuotePrice(), remainAmount, buyQuote.getQuoteType(), commissionPercent, null,minCommissionFee,null);
            //计算佣金
            BigDecimal commissionFee = ArithUtil.scale(rsArr[0]);
            //减掉佣金和税
            BigDecimal rsMoney = ArithUtil.scale(rsArr[1]);

            buyQuote.setFrozeMoney(rsMoney);
            buyQuote.setCommissionFee(commissionFee);
            //交易成功，股票交易数量减少
            buyQuote.setCurrentAmount(remainAmount);

            //卖家移出队列
            boolean sgrs = stQuoteService.deleteQuoteFromQueue(sellQuote);

            if(!sgrs){
                throw new TradeBusinessException("交易卖家移出队列失败");
            }
            //修改买家卖家报价状态
            buyQuote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_DEALING);
            sellQuote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_ALREADYDEAL);
            if(buyQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL) {
//                iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(buyQuote));
            }else if(buyQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_REAL){
                stQuoteService.updateQuote(buyQuote);
            }
            if(sellQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_VIRTUAL) {
//                iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(sellQuote));
            }else if(sellQuote.getUserType() == ApplicationConstants.ACCOUNT_TYPE_REAL){
                stQuoteService.updateQuote(sellQuote);
            }
        }
        System.out.println(BusinessConstants.stTradeQueueMap);
    }

    @Override
    public List<StTradeRecord> findTradeRecordListByStock(SearchTradeRecordDTO searchTradeRecordDTO) {
        List<StTradeRecord> stTradeRecordList = null;
        Object tradeRecordListObj = null;
        if (StringUtils.isEmpty(searchTradeRecordDTO.getStockId())) {
            tradeRecordListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_TRADERECORDLIST, null);
        } else {
            tradeRecordListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_TRADERECORDLIST_STOCKID, searchTradeRecordDTO.getStockId(), null);
        }
        // 单只股票的交易记录列表
        if (tradeRecordListObj != null) {
            stTradeRecordList = (LinkedList<StTradeRecord>) tradeRecordListObj;
        }
        return stTradeRecordList;
    }

    @Override
    public List<StTradeRecord> findTradeRecordList(SearchTradeRecordDTO searchTradeRecordDTO) {
        StTradeRecord record = new StTradeRecord();
        record.setSellerAccountId(searchTradeRecordDTO.getAccountId());
        record.setBuyerAccountId(searchTradeRecordDTO.getAccountId());
        record.setStockId(searchTradeRecordDTO.getStockId());

        Long startTime = searchTradeRecordDTO.getStartDate()==null?null:searchTradeRecordDTO.getStartDate().getTime();
        Long endTime = searchTradeRecordDTO.getEndDate()==null?null:searchTradeRecordDTO.getEndDate().getTime();
        return stTradeRecordDao.getTList(record, startTime, endTime, searchTradeRecordDTO.getLimit(), searchTradeRecordDTO.getOffset());
    }

    /**
     * 交易处理
     * @param buyQuote
     * @param sellQuote
     * @param tradeStockAmount
     */
    private boolean saveTrading(StQuote buyQuote, StQuote sellQuote, long tradeStockAmount) throws Exception {

        boolean brs = false, srs = false, rs = false;
        //股票交易价格
        BigDecimal tradeStockQuotePrice = null;

        tradeStockQuotePrice = judgeQuotePrice(buyQuote, sellQuote, tradeStockAmount);

        //交易成功，交易买家持仓增加
        brs = stPositionService.updatePositionAdd(buyQuote.getAccountId(), buyQuote.getStockId(), tradeStockAmount);

        //佣金比例
        String commissionPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_COMMINSSION_PERCENT);
        //印花税比例
        String taxPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_TAX_PERCENT);
        //最小佣金值
        String minCommissionFee = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_COMMISIONFEE_MIN);
        //最小印花税值
        String minTaxFee = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_TAXFEE_MIN);

        //买家购买股票消费资产
        BigDecimal[] brsArr = Utils.calculate(tradeStockQuotePrice, tradeStockAmount, buyQuote.getQuoteType(), commissionPercent, null,minCommissionFee,null);
        BigDecimal buyerCommissionFee = ArithUtil.scale(brsArr[0]);
        BigDecimal buyerCostMoney = ArithUtil.scale(brsArr[1]);

        //卖家新增费用计算
        BigDecimal[] srsArr = Utils.calculate(tradeStockQuotePrice, tradeStockAmount, sellQuote.getQuoteType(), commissionPercent, taxPercent, minCommissionFee, minTaxFee);
        BigDecimal sellerCommissionFee = ArithUtil.scale(srsArr[0]);
        BigDecimal taxFee = ArithUtil.scale(srsArr[2]);
        //减掉佣金和税
        BigDecimal sellerGetMoney = ArithUtil.scale(srsArr[1]);

        //交易成功，交易卖家资产增加
        srs = stAccountService.updateStAccountMoneyAdd(sellQuote.getAccountId(), sellerGetMoney);

        if(brs&&srs) {
            //添加交易记录
            StTradeRecord record = new StTradeRecord();
            record.setRecordId(UUIDUtils.uuidTrimLine());
            record.setSellerAccountId(sellQuote.getAccountId());
            record.setBuyerAccountId(buyQuote.getAccountId());
            record.setStockId(buyQuote.getStockId());
            record.setQuotePrice(tradeStockQuotePrice);
            record.setAmount(tradeStockAmount);
            record.setBuyFee(buyerCommissionFee);
            record.setSellFee(sellerCommissionFee);
            record.setDealTax(taxFee);
            record.setDateTime(System.currentTimeMillis());

//            this.addTradeRecord(record);
            boolean isAddSuccess = stTradeRecordDao.add(record) > 0;
            if(!isAddSuccess){
                throw new TradeBusinessException("交易记录添加失败");
            }

            // 更新交易记录到缓存,总的交易记录列表
            LinkedList<StTradeRecord> stTradeRecordList = null;
            Object traderecordListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_TRADERECORDLIST, null);
            if (traderecordListObj != null) {
                stTradeRecordList = (LinkedList<StTradeRecord>) traderecordListObj;
                if (stTradeRecordList.size()>20) {
                    stTradeRecordList.removeFirst();
                }
            } else {
                stTradeRecordList = new LinkedList<StTradeRecord>();
            }
            stTradeRecordList.add(record);
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_TRADERECORDLIST, stTradeRecordList);

            // 单只股票的交易记录列表
            Object tradeRecordListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_TRADERECORDLIST_STOCKID, record.getStockId(), null);
            if (tradeRecordListObj!=null) {
                stTradeRecordList = (LinkedList<StTradeRecord>) tradeRecordListObj;
                if (stTradeRecordList.size() > 20) {
                    stTradeRecordList.removeFirst();
                }
            } else {
                stTradeRecordList = new LinkedList<StTradeRecord>();
            }
            stTradeRecordList.add(record);
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_TRADERECORDLIST_STOCKID, record.getStockId(), stTradeRecordList, 60*60*4);

            //添加资金流水
            List<StMoneyJournal> moneyJournals = new ArrayList<StMoneyJournal>();

            StMoneyJournal buyMoneyJournal = new StMoneyJournal();
            buyMoneyJournal.setRecordId(UUIDUtils.uuidTrimLine());
            buyMoneyJournal.setStockId(buyQuote.getStockId());
            buyMoneyJournal.setAccountId(buyQuote.getAccountId());
            buyMoneyJournal.setQuoteId(buyQuote.getQuoteId());
            buyMoneyJournal.setAmount(tradeStockAmount);
            buyMoneyJournal.setQuotePrice(tradeStockQuotePrice);
            buyMoneyJournal.setDealMoney(buyerCostMoney);
            buyMoneyJournal.setDealType(buyQuote.getQuoteType());
            buyMoneyJournal.setDealFee(buyerCommissionFee);
            buyMoneyJournal.setDateTime(System.currentTimeMillis());

            moneyJournals.add(buyMoneyJournal);

            StMoneyJournal sellMoneyJournal = new StMoneyJournal();
            sellMoneyJournal.setRecordId(UUIDUtils.uuidTrimLine());
            sellMoneyJournal.setStockId(sellQuote.getStockId());
            sellMoneyJournal.setAccountId(sellQuote.getAccountId());
            sellMoneyJournal.setQuoteId(sellQuote.getQuoteId());
            sellMoneyJournal.setAmount(tradeStockAmount);
            sellMoneyJournal.setQuotePrice(tradeStockQuotePrice);
            sellMoneyJournal.setDealMoney(sellerGetMoney);
            sellMoneyJournal.setDealType(sellQuote.getQuoteType());
            sellMoneyJournal.setDealFee(sellerCommissionFee);
            sellMoneyJournal.setDealTax(taxFee);
            sellMoneyJournal.setDateTime(System.currentTimeMillis());

            moneyJournals.add(sellMoneyJournal);

            rs = stMoneyJournalService.saveMoneyJournalList(moneyJournals);
        }

        return rs;
    }


    /**
     * 判断买家卖家报价
     * @param buyQuote
     * @param sellQuote
     * @param tradeStockAmount
     */
    private BigDecimal judgeQuotePrice(StQuote buyQuote, StQuote sellQuote, long tradeStockAmount) throws Exception{

        BigDecimal tradeStockQuotePrice = null;
        //买家报价大于卖家报价
        if(ArithUtil.compare(buyQuote.getQuotePrice(), sellQuote.getQuotePrice())==1){
            //如果卖家报价早于买家报价
            if(sellQuote.getDateTime().longValue() < buyQuote.getDateTime().longValue()){
                //交易价格取卖家报价
                tradeStockQuotePrice = sellQuote.getQuotePrice();

                //节省成本
                BigDecimal saveMoney = null;

                //佣金比例
                String commissionPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_COMMINSSION_PERCENT);
                //最小佣金值
                String minCommissionFee = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_COMMISIONFEE_MIN);

                BigDecimal[] brsArr = Utils.calculate(buyQuote.getQuotePrice(), tradeStockAmount, buyQuote.getQuoteType(), commissionPercent, null, minCommissionFee, null);
                //以买家报价计算交易成本
                BigDecimal buyCost = ArithUtil.scale(brsArr[1]);

                BigDecimal[] drsArr = Utils.calculate(tradeStockQuotePrice, tradeStockAmount, buyQuote.getQuoteType(), commissionPercent, null, minCommissionFee, null);
                //以交易价格计算交易成本
                BigDecimal dealCost = ArithUtil.scale(drsArr[1]);

                //节省成本
                saveMoney = ArithUtil.subtract(buyCost, dealCost);
                //将节省成本归还买家
                stAccountService.updateStAccountMoneyAdd(buyQuote.getAccountId(), saveMoney);

            }else if(sellQuote.getDateTime().longValue() >= buyQuote.getDateTime().longValue()){ //如果买家报价早于或等于卖家报价
                //交易价格取买家报价
                tradeStockQuotePrice = buyQuote.getQuotePrice();
            }
        }else{//买家报价等于卖家报价
            tradeStockQuotePrice = buyQuote.getQuotePrice();
        }
        return tradeStockQuotePrice;
    }
}
