package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.dto.AutomatedTradingDTO;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StStock;
import com.ryd.business.service.AutomatedTradingService;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动化交易
 * Created by chenji on 2016/5/3.
 */
@Service
public class AutomatedTradingServiceImpl implements AutomatedTradingService {
    @Autowired
    private StStockService stStockService;
    @Autowired
    private ICacheService iCacheService;
    @Autowired
    private StQuoteService stQuoteService;

    @Override
    public boolean addAutomatedTradingByStock(AutomatedTradingDTO automatedTradingDTO) {
        SearchStockDTO searchStockDTO = new SearchStockDTO();
        searchStockDTO.setStockId(automatedTradingDTO.getStockId());
        StStock stStock = stStockService.findStockListByStock(searchStockDTO);
        double minBuyPrice = stStock.getBuyFivePrice()-0.01;
        double maxSellPrice = stStock.getBuyFivePrice()+0.01;
        List<StQuote> quoteList = new ArrayList<StQuote>();
        StQuote buyQuote = new StQuote();
        buyQuote.setStockId(searchStockDTO.getStockId());
        buyQuote.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_BUY);
        buyQuote.setAmount(100L);
        buyQuote.setAccountId(automatedTradingDTO.getAccountId());
        buyQuote.setUserType(ApplicationConstants.ACCOUNT_TYPE_VIRTUAL);
        buyQuote.setQuotePrice(BigDecimal.valueOf(minBuyPrice));
        quoteList.add(buyQuote);

        StQuote sellQuote = new StQuote();
        sellQuote.setStockId(searchStockDTO.getStockId());
        sellQuote.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_BUY);
        sellQuote.setAmount(100L);
        sellQuote.setAccountId(automatedTradingDTO.getAccountId());
        sellQuote.setUserType(ApplicationConstants.ACCOUNT_TYPE_VIRTUAL);
        sellQuote.setQuotePrice(BigDecimal.valueOf(maxSellPrice));
        quoteList.add(sellQuote);

        try {
            stQuoteService.saveQuoteList(quoteList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
