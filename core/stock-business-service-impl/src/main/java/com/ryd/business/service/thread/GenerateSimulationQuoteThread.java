package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.cache.service.ICacheService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 生成模拟订单线程
 * Created by chenji on 2016/4/28.
 */
public class GenerateSimulationQuoteThread implements Runnable {
    CountDownLatch cdOrder;
    CountDownLatch cdAddQuote;
    private ICacheService iCacheService;
    private StQuoteService stQuoteService;
    private String stockCode;

    public GenerateSimulationQuoteThread(CountDownLatch cdOrder, CountDownLatch cdAddQuote, String stockCode, ICacheService iCacheService, StQuoteService stQuoteService) {
        this.cdOrder = cdOrder;
        this.cdAddQuote = cdAddQuote;
        this.iCacheService = iCacheService;
        this.stQuoteService = stQuoteService;
        this.stockCode = stockCode;
    }

    @Override
    public void run() {
        List<SimulationQuoteDTO> simulationQuoteDTOList = null;
//        Object simulationListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, stockCode, null);
//        if (simulationListObj!=null) {
//            simulationQuoteDTOList=(List<SimulationQuoteDTO>) simulationListObj;
//        }
        simulationQuoteDTOList = BusinessConstants.simulateQuoteMap.get(stockCode);
        if (!StringUtils.isEmpty(simulationQuoteDTOList)) {
            List<StQuote> stQuoteList = new ArrayList<StQuote>();
            for (SimulationQuoteDTO simulationQuoteDTO : simulationQuoteDTOList) {
                StQuote quote = new StQuote();
                quote.setAccountId("800891cdc704462ab0c2335460a91684");
                quote.setStockId(simulationQuoteDTO.getStockId());
                quote.setUserType(ApplicationConstants.ACCOUNT_TYPE_VIRTUAL); // 马甲用户
                quote.setQuoteType(simulationQuoteDTO.getQuoteType());
                quote.setAmount(simulationQuoteDTO.getAmount());
                quote.setQuotePrice(BigDecimal.valueOf(simulationQuoteDTO.getQuotePrice()));
                quote.setQuoteId(UUIDUtils.uuidTrimLine());
                quote.setDateTime(System.currentTimeMillis());
                // 用于排序的字段
                long timeSort = Integer.parseInt(String.valueOf(quote.getDateTime()).substring(7));
                quote.setTimeSort(timeSort);

                quote.setCurrentAmount(quote.getAmount());
                quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
                stQuoteList.add(quote);
            }
            try {
                cdOrder.await();
                stQuoteService.saveQuoteList(stQuoteList, ApplicationConstants.ACCOUNT_TYPE_VIRTUAL);
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                cdAddQuote.countDown();
            }
        }
    }
}
