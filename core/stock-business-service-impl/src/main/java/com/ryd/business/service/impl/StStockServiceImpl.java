package com.ryd.business.service.impl;

import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.dao.StStockDao;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.dto.StStockDetailDTO;
import com.ryd.business.dto.StStockTurnoverDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.StStockConfigService;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.thread.SyncStockThread;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
    public boolean executeRealTimeStockInfo() {
//        ExecutorService stockService = Executors.newFixedThreadPool(10);
        BlockingQueue<Runnable> stockQueue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor stockService = new ThreadPoolExecutor(20, 20, 1, TimeUnit.MINUTES, stockQueue);
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务

        List<StStockConfig> list = stStockConfigService.findStockConfig(null, 1, Integer.MAX_VALUE);
        int count = list.size()/10+1;
        cdOrder.countDown();
        final CountDownLatch cdAnswer = new CountDownLatch(count);
        // 清除模拟报价
        BusinessConstants.simulateQuoteMap.clear();
        // 清除最近的股票实时价格信息
        BusinessConstants.tempStockPriceMap.clear();
        StringBuffer stockCodeStringBuffer = new StringBuffer();
//        int i = 1;
//        int j = 1;
        for (StStockConfig stock: list) {
            stockCodeStringBuffer.append(stock.getStockTypeName()+stock.getStockCode()).append(",");
            int length = stockCodeStringBuffer.toString().split(",").length;
            if (length==10) {
                // 同步股票信息
                stockService.execute(new SyncStockThread(stockCodeStringBuffer.toString(), iCacheService, cdOrder, cdAnswer, this));
                stockCodeStringBuffer = new StringBuffer();
                int threadSize = stockQueue.size();
//                System.out.println("线程队列大小为-->"+threadSize);
//                System.out.println("current thread index:" + j);
//                j++;
            }
//            System.out.println("current index:" + i);
//            i++;
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
        System.out.println("模拟订单数：" + BusinessConstants.simulateQuoteMap.size());
        BusinessConstants.stockPriceMap.clear();
        BusinessConstants.stockPriceMap.putAll(BusinessConstants.tempStockPriceMap);
        System.out.println("股票实时价格-数量：" + BusinessConstants.stockPriceMap.size());
        iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, BusinessConstants.stockPriceMap);
        iCacheService.setObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, BusinessConstants.simulateQuoteMap);
//        iCacheService.setObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, BusinessConstants.simulateQuoteMap);
        stockService.shutdownNow();
        return true;
    }

    @Override
    public List<StStock> findStockList(SearchStockDTO searchStockDTO) {
        List<StStock> stockList = null;
        stockList = new ArrayList<StStock>(BusinessConstants.stockPriceMap.values());

//        Object stockObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, null);
//        if (stockObj != null) {
//            stockList = (List<StStock>)stockObj;
//        } else {
////            stockList = stStockDao.findStStockListCurrentTime(searchStockDTO);
//            // TODO 缓存没有，暂时不取
//        }
        /*ConcurrentHashMap<String, List<StStock>> stockPriceMap = null;
        if (BusinessConstants.stockPriceMap != null && BusinessConstants.stockPriceMap.size()>0) {
            stockPriceMap = BusinessConstants.stockPriceMap;
        } else {
            Object stockPriceMapObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, null);
            if (stockPriceMapObj!=null) {
                stockPriceMap = (ConcurrentHashMap<String, List<StStock>>) stockPriceMapObj;
            }
        }

        if (stockPriceMap!=null&&stockPriceMap.size()>0) {
            Collection<List<StStock>> list = stockPriceMap.values();
            if (list!=null&&list.size()>0) {
                stockList = new ArrayList<StStock>();
                for (List<StStock> sList : list) {
                    if (sList==null) continue;
                    stockList.addAll(sList);
                }
            }
        }*/
        return stockList;
    }

    @Override
    public StStock findStockListByStock(SearchStockDTO searchStockDTO) {
        StStock stStock = null;
        String stockCode = stStockConfigService.getStockCodeByStockId(searchStockDTO.getStockId());
        stStock = BusinessConstants.stockPriceMap.get(stockCode);
        if (stStock==null) {
            this.findStockListToCache();
            stStock = BusinessConstants.stockPriceMap.get(stockCode);
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

    @Override
    public boolean findStockListToCache() {
        Object obj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, null);
        if (obj != null) {
            BusinessConstants.stockPriceMap = (ConcurrentHashMap<String, StStock>) obj;
        }
        return true;
    }

    @Override
    public List<StStockTurnoverDTO> findStockTradeTurnoverList(SearchStockDTO searchStockDTO) {
        List<StStockTurnoverDTO> stockTradeAmountDTOList = new ArrayList<StStockTurnoverDTO>();
        return stockTradeAmountDTOList;
    }
}