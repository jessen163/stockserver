package com.ryd.business.service.impl;

import com.ryd.business.dao.StStockDao;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.StStockConfigService;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.thread.SyncStockThread;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final CountDownLatch cdAnswer = new CountDownLatch(list.size());

        // 执行命令
        cdOrder.countDown();

        StringBuffer stockCodeStringBuffer = new StringBuffer();
        for (StStockConfig stock: list) {
            stockCodeStringBuffer.append(stock.getStockTypeName()+stock.getStockCode()).append(",");
        }
        // 同步股票信息，同时生成马甲订单
        stockService.execute(new SyncStockThread(stockCodeStringBuffer.toString(), iCacheService, cdOrder, cdAnswer, this));
        try {
            // 等待任务执行完成
            cdAnswer.await();
            // 任务执行完成，TODO 通知交易引擎运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<StStock> findStockList(SearchStockDTO searchStockDTO) {
        return null;
    }

    @Override
    public StStock findStockListByStock(SearchStockDTO searchStockDTO) {
        // 返回前一天的收盘价
        return null;
    }
}
