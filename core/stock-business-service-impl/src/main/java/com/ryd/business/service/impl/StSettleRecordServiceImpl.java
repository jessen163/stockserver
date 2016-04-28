package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.business.dao.StSettleRecordDao;
import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.service.*;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.service.StDateScheduleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * <p>标题:结算ServiceImpl</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：songby
 * 创建时间：2016/4/26 15:20
 */
@Service
public class StSettleRecordServiceImpl implements StSettleRecordService {

    @Autowired
    private StSettleRecordDao stSettleRecordDao;
    @Autowired
    private StStockService stStockService;
    @Autowired
    private StAccountService stAccountService;
    @Autowired
    private StDateScheduleService stDateScheduleService;
    @Autowired
    private StQuoteService stQuoteService;
    @Autowired
    private StPositionService stPositionService;
    @Autowired
    private ICacheService iCacheService;

    @Override
    public void updateStockSettling() {
        //判断是否可以结算
       if(stDateScheduleService.getIsCanSettle()){
           // 结算
           List<String> stockCodeList = stQuoteService.findQuoteStockIdList();
           for(String stockCode : stockCodeList) {

               SearchQuoteDTO searchQuoteDTO = new SearchQuoteDTO();
               searchQuoteDTO.setStockCode(stockCode);
               searchQuoteDTO.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_BUY);

               List<StQuote> buyList = stQuoteService.findQuoteQueueByStock(searchQuoteDTO);
               searchQuoteDTO.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_SELL);
               List<StQuote> sellList = stQuoteService.findQuoteQueueByStock(searchQuoteDTO);

               if(CollectionUtils.isEmpty(buyList) && CollectionUtils.isEmpty(sellList)){
                   continue;
               }else{
                   //买家结算
                   if(CollectionUtils.isNotEmpty(buyList)){
                       for(StQuote quote : buyList){
                           if(quote==null){
                               continue;
                           }
                          //撤回托管买股票费用,帐户增加资产
                          boolean rs = stAccountService.updateStAccountMoneyAdd(quote.getAccountId(),quote.getFrozeMoney());
                          if(rs){
                              //修改报价状态
                              updateQuoteStatus(quote);
                              //从队列清除报价
                              stQuoteService.deleteQuoteFromQueue(quote);
                          }
                       }
                   }

                   //卖家结算
                   if(CollectionUtils.isNotEmpty(sellList)){
                       for(StQuote squote : sellList){
                           if(squote==null){
                               continue;
                           }
                           //撤回托管为卖的股票，持仓数量增加
                           boolean srs = stPositionService.updatePositionAdd(squote.getAccountId(), squote.getStockId(), squote.getCurrentAmount());
                           if(srs){
                               //修改报价状态
                               updateQuoteStatus(squote);
                               //从队列清除报价
                               stQuoteService.deleteQuoteFromQueue(squote);
                           }
                       }
                   }
               }
           }
       }
    }

    /**
     * 修改报价状态
     * @param quote
     */
    private void updateQuoteStatus(StQuote quote){
        switch (quote.getStatus()){
            case 1:
                quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_NOTDEAL);
                break;
            case 2:
                quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_PARTDEAL);
                break;
            default:
                break;
        }
        stQuoteService.updateQuote(quote);
    }
}
