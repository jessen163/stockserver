package com.ryd.business.service.impl;

import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.dao.StStockDao;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.dto.StStockDetailDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.StStockConfigService;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.thread.SyncStockThread;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>标题:股票业务实现类</p>
 * <p>描述:股票业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
@Service
public class StStockServiceImpl implements StStockService {
    @Autowired
    private StStockDao stStockDao;
    @Autowired
    private StStockConfigService stStockConfigService;
    @Autowired
    private ICacheService iCacheService;

    @Override
    public boolean saveStockBatch(List<StStock> stStockList) {
        return stStockDao.saveStockBatch(stStockList);
    }

    @Override
    public boolean updateRealTimeStockInfo() {
        ExecutorService stockService = Executors.newFixedThreadPool(10);
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务

        List<StStockConfig> list = stStockConfigService.findStockConfig(null, 1, Integer.MAX_VALUE);
        int count = list.size()/10+1;
        cdOrder.countDown();
        // TODO 问题
        count = count - 35;
        final CountDownLatch cdAnswer = new CountDownLatch(count);
        StringBuffer stockCodeStringBuffer = new StringBuffer();;
        for (StStockConfig stock: list) {
            stockCodeStringBuffer.append(stock.getStockTypeName()+stock.getStockCode()).append(",");
            if (stockCodeStringBuffer.toString().split(",").length==11) {
                // 同步股票信息
                stockService.execute(new SyncStockThread(stockCodeStringBuffer.toString(), iCacheService, cdOrder, cdAnswer, this));
                stockCodeStringBuffer = new StringBuffer();
            }
        }
        if (!stockCodeStringBuffer.toString().isEmpty()) {
            stockService.execute(new SyncStockThread(stockCodeStringBuffer.toString(), iCacheService, cdOrder, cdAnswer, this));
        }

        try {
            // 等待任务执行完成
            cdAnswer.await();
            // 股票下载任务执行完成，通知交易引擎停止运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<StStock> findStockList(SearchStockDTO searchStockDTO) {
        List<StStock> stockList = null;
        Object stockObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, null);
        if (stockObj != null) {
            stockList = (List<StStock>)stockObj;
        } else {
//            stockList = stStockDao.findStStockListCurrentTime(searchStockDTO);
            // TODO 缓存没有，暂时不取
        }
        return stockList;
    }

    @Override
    public StStock findStockListByStock(SearchStockDTO searchStockDTO) {
        StStock stStock = null;
//        StStockConfig stockConfig = null;
//        if (iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCKCONFIGLIST_MAP, searchStockDTO.getStockId(), null)!=null) {
//            stockConfig = (StStockConfig)iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCKCONFIGLIST_MAP, searchStockDTO.getStockId(), null);
//        }
        if (searchStockDTO!=null&&searchStockDTO.getStockId()!=null) {
            Object stockPriceObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICEMAP, searchStockDTO.getStockId(), null);
            // 返回前一天的收盘价
            if (stockPriceObj != null) {
                stStock = (StStock) stockPriceObj;
            } else {
                // TODO 从数据库获取最新的报价信息
            }
        }
        return stStock;
    }

    @Override
    public StStockDetailDTO findStockDetailByStock(SearchStockDTO searchStockDTO) {
        StStockDetailDTO stStockDetailDTO = new StStockDetailDTO();
        StStock stStock = this.findStockListByStock(searchStockDTO);
        if (!StringUtils.isEmpty(stStock)) {
            stStockDetailDTO.setStStock(stStock);
        }
        // TODO 增加股票价格、增加股票成交量
        return stStockDetailDTO;
    }
}