package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.HttpclientUtil;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.StStockService;
import com.ryd.cache.service.ICacheService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/**
 *
 * 同步股票实时价格信息
 * Created by Administrator on 2016/4/9.
 */
public class SyncStockThread implements Runnable {

    private static Logger logger = Logger.getLogger(SyncStockThread.class);

    private final StStockConfig stock;

    private ICacheService cacheService;

    private StStockService stStockService;

    private CountDownLatch cdOrder;

    private CountDownLatch cdAnswer;

    public SyncStockThread(StStockConfig stStock, ICacheService cacheService, CountDownLatch cdOrder, CountDownLatch cdAnswer, StStockService stStockService) {
        this.stock = stStock;
        this.cacheService = cacheService;
        this.stStockService = stStockService;
        this.cdOrder = cdOrder;
        this.cdAnswer = cdAnswer;
    }

    @Override
    public void run() {
//            logger.info("更新股票实时信息.............start...........");
        try {
            LinkedList<StStock> stStockList = null;
            Object obj = cacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, stock.getStockCode(), null);
            if (obj != null) {
                stStockList = (LinkedList<StStock>) obj;
            }

            if (stStockList == null) {
                stStockList = new LinkedList<StStock>();
            }
            cdOrder.await();
            // 下载单只股票信息
            String stockStr = HttpclientUtil.doGet(ApplicationConstants.STOCK_SERVER_URL + (stock.getStockType().intValue() == 1 ? "sh" : "sz") + stock.getStockCode());
            StStock newStock = getStockByStr(stock.getStockCode(), stockStr);
            // stockGetInfoFromApiI.getStStockInfo(stock.getStockType(), stock.getStockCode());
            // 保存股票价格等信息
            stStockService.saveStockBatch(Arrays.asList(newStock));
            if (stStockList.size() == 5) {
                stStockList.removeFirst();
            }
            stStockList.add(newStock);

            cacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, stock.getStockCode(), stStockList, 60 * 60 * 1000);
            // TODO 生成马甲订单
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cdAnswer.countDown();
        }
    }

    private StStock getStockByStr(String stockCode, String stockStr) {
        String[] s = stockStr.split("\"");
        String[] sk = null;
        StStock sts = null;
        StringBuilder bf = new StringBuilder();
        if (!s[1].equals("")) {
//                    bf.append(stockCode + ",");
            bf.append(s[1]);
            int n = bf.lastIndexOf(",");
            String string = bf.substring(0, n);
            sk = string.split(",");

            sts = new StStock();
//            sts.setStockId(stockCode);
            sts.setStockCode(stockCode);
            sts.setStockName(sk[0]);
            sts.setOpenPrice(BigDecimal.valueOf(Double.parseDouble(sk[1])));
            sts.setBfclosePrice(BigDecimal.valueOf(Double.parseDouble(sk[2])));
            sts.setCurrentPrice(BigDecimal.valueOf(Double.parseDouble(sk[3])));
            sts.setMaxPrice(BigDecimal.valueOf(Double.parseDouble(sk[4])));
            sts.setMinPrice(BigDecimal.valueOf(Double.parseDouble(sk[5])));
            sts.setBiddingBuyPrice(BigDecimal.valueOf(Double.parseDouble(sk[6])));
            sts.setBiddingSellPrice(BigDecimal.valueOf(Double.parseDouble(sk[7])));
            sts.setTradeAmount(Long.valueOf(sk[8]));
            sts.setTradeMoney(BigDecimal.valueOf(Double.parseDouble(sk[9])));

            sts.setBuyOneAmount(Long.valueOf(sk[10]));
            sts.setBuyOnePrice(BigDecimal.valueOf(Double.parseDouble(sk[11])));
            sts.setBuyTwoAmount(Long.valueOf(sk[12]));
            sts.setBuyTwoPrice(BigDecimal.valueOf(Double.parseDouble(sk[13])));
            sts.setBuyThreeAmount(Long.valueOf(sk[14]));
            sts.setBuyThreePrice(BigDecimal.valueOf(Double.parseDouble(sk[15])));
            sts.setBuyFourAmount(Long.valueOf(sk[16]));
            sts.setBuyFourPrice(BigDecimal.valueOf(Double.parseDouble(sk[17])));
            sts.setBuyFiveAmount(Long.valueOf(sk[18]));
            sts.setBuyFivePrice(BigDecimal.valueOf(Double.parseDouble(sk[19])));

            sts.setSellOneAmount(Long.valueOf(sk[20]));
            sts.setSellOnePrice(BigDecimal.valueOf(Double.parseDouble(sk[21])));
            sts.setSellTwoAmount(Long.valueOf(sk[22]));
            sts.setSellTwoPrice(BigDecimal.valueOf(Double.parseDouble(sk[23])));
            sts.setSellThreeAmount(Long.valueOf(sk[24]));
            sts.setSellThreePrice(BigDecimal.valueOf(Double.parseDouble(sk[25])));
            sts.setSellFourAmount(Long.valueOf(sk[26]));
            sts.setSellFourPrice(BigDecimal.valueOf(Double.parseDouble(sk[27])));
            sts.setSellFiveAmount(Long.valueOf(sk[28]));
            sts.setSellFivePrice(BigDecimal.valueOf(Double.parseDouble(sk[29])));
        }
        return sts;
    }
}