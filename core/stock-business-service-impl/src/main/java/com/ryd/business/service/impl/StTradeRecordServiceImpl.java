package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.business.dao.StTradeRecordDao;
import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.dto.SearchTradeRecordDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StTradeRecord;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.StTradeRecordService;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    private StStockService stStockService;

    @Override
    public boolean saveTradeRecordBatch(List<StTradeRecord> tradeRecordList) {
        return false;
    }

    @Override
    public void updateStockTrading() {
        // 启动交易线程
        // 检查交易时间
        // 从队列获取买卖报价
        // 匹配买卖报价成功，记录交易日志，更新买、卖双方账户信息，更新仓位信息
        // 记录资金流水，更新双方报价信息

        // 操作过程中（每分钟）停止交易，获取股票信息（调用股票更新方法），生成马甲订单，完成后重新启动交易方法
        ExecutorService tradingservice = Executors.newFixedThreadPool(10);

        SearchQuoteDTO searchQuoteDTO = new SearchQuoteDTO();
        searchQuoteDTO.setStockCode("1");
        searchQuoteDTO.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_BUY);
        StQuote buyQuote = stQuoteService.findFirstQuoteByStock(searchQuoteDTO);
        searchQuoteDTO.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_SELL);
        StQuote sellQuote = stQuoteService.findFirstQuoteByStock(searchQuoteDTO);
        if (buyQuote!=null&&sellQuote!=null) {
            // 如果撮合成功, 执行交易, 同时更新买入、卖出队列
            if (ArithUtil.compare(buyQuote.getQuotePrice(), sellQuote.getQuotePrice()) >= 0 && !buyQuote.getAccountId().equals(sellQuote.getAccountId())) {
                // 调用交易接口

                // 从队列中删除报价，同时修改报价状态
                stQuoteService.deleteQuoteFromQueue(buyQuote);
                stQuoteService.deleteQuoteFromQueue(sellQuote);
            }
        }

    }


    @Override
    public void updateStockSettling() {
        // 结算
    }

    @Override
    public List<StTradeRecord> findTradeRecordListByStock(SearchTradeRecordDTO searchTradeRecordDTO) {
        return null;
    }

    @Override
    public List<StTradeRecord> findTradeRecordList(SearchTradeRecordDTO searchTradeRecordDTO) {
        return null;
    }
}
