package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.HttpclientUtil;
import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;
import com.ryd.cache.service.ICacheService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * 同步股票实时价格信息
 * Created by Administrator on 2016/4/9.
 */
public class SyncStockThread implements Runnable {

    private static Logger logger = Logger.getLogger(SyncStockThread.class);

    private String stockCodeStr;

    private ICacheService cacheService;

    private StStockService stStockService;

    private CountDownLatch cdOrder;

    private CountDownLatch cdAnswer;

    public SyncStockThread(String stockCodeStr, ICacheService cacheService, CountDownLatch cdOrder, CountDownLatch cdAnswer, StStockService stStockService) {
        this.stockCodeStr = stockCodeStr;
        this.cacheService = cacheService;
        this.stStockService = stStockService;
//        this.cdOrder = cdOrder;
//        this.cdAnswer = cdAnswer;
    }

    @Override
    public void run() {
//            logger.info("更新股票实时信息.............start...........");
        try {
//            StringBuffer stockCodeStringBuffer = new StringBuffer();
//            for (StStockConfig stStockConfig: stStockConfigList) {
//                stockCodeStringBuffer.append(stStockConfig.getStockTypeName()+stStockConfig.getStockCode()).append(",");
//            }

//            LinkedList<StStock> stStockList = null;
//            Object obj = cacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, stock.getStockCode(), null);
//            if (obj != null) {
//                stStockList = (LinkedList<StStock>) obj;
//            }
//
//            if (stStockList == null) {
//                stStockList = new LinkedList<StStock>();
//            }
//            cdOrder.await();
            // 下载单只股票信息
            String stockInfoStr = HttpclientUtil.doGet(ApplicationConstants.STOCK_SERVER_STOCKBASE_URL + stockCodeStr);
//            String stockStr = HttpclientUtil.doGet(ApplicationConstants.STOCK_SERVER_STOCKBASE_URL + (stock.getStockType().intValue() == 1 ? "sh" : "sz") + stock.getStockCode());
//            StStock newStock = getStockByStr(stock.getStockCode(), stockStr);
            // stockGetInfoFromApiI.getStStockInfo(stock.getStockType(), stock.getStockCode());
            // 通过字符串转换成股票信息
            List<StStock> stockList = getStockInfoByStr(stockCodeStr, stockInfoStr);

            // 保存股票价格等信息
            stStockService.saveStockBatch(stockList);
            addSimulationQuote(stockList);
//            if (stStockList.size() == 5) {
//                stStockList.removeFirst();
//            }
//            stStockList.add(newStock);

//            cacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, stock.getStockCode(), stStockList, 60 * 60 * 1000);

//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            cdAnswer.countDown();
        }
    }

    private List<StStock> getStockInfoByStr(String stockCodeStr, String stockListStr) {
        List<StStock> stockList = new ArrayList<StStock>();

        String[] stockCodeStrArr = stockCodeStr.split(";");
        String[] stockListStrArr = stockListStr.split(";");
        for (int i = 0; i < stockCodeStrArr.length; i++) {
            stockList.add(getStockByStr(stockCodeStrArr[i], stockListStrArr[i].trim()));
        }

        return stockList;
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
            sts.setStockCode(stockCode.substring(2));// 获取股票代码
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

    /**
     * 生成马甲盘,同时将实时价格放入缓存，缓存10分钟
     */
    public boolean addSimulationQuote(List<StStock> stockList) {
        BigDecimal[] priceArr = new BigDecimal[10];
        long[] amountArr = new long[10];
        short[] typeArr = new short[10];
        // 模拟订单
        List<SimulationQuoteDTO> simulationQuoteDTOList = new ArrayList<SimulationQuoteDTO>();
        List<StStock> stStockCacheList = new ArrayList<StStock>();
        for (StStock stStock : stockList) {
            // 将实时价格放入缓存  6分钟
            cacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICEMAP, stStock.getStockCode(), stStock, 60*10);
            stStockCacheList.add(stStock);
            priceArr[0]=stStock.getBuyOnePrice();
            priceArr[1]=stStock.getBuyTwoPrice();
            priceArr[2]=stStock.getBuyThreePrice();
            priceArr[3]=stStock.getBuyFourPrice();
            priceArr[4]=stStock.getBuyFivePrice();
            priceArr[5]=stStock.getSellOnePrice();
            priceArr[6]=stStock.getSellTwoPrice();
            priceArr[7]=stStock.getSellThreePrice();
            priceArr[8]=stStock.getSellFourPrice();
            priceArr[9]=stStock.getSellFivePrice();

            amountArr[0]=stStock.getBuyOneAmount();
            amountArr[1]=stStock.getBuyTwoAmount();
            amountArr[2]=stStock.getBuyThreeAmount();
            amountArr[3]=stStock.getBuyFourAmount();
            amountArr[4]=stStock.getBuyFiveAmount();
            amountArr[5]=stStock.getSellOneAmount();
            amountArr[6]=stStock.getSellTwoAmount();
            amountArr[7]=stStock.getSellThreeAmount();
            amountArr[8]=stStock.getSellFourAmount();
            amountArr[9]=stStock.getSellFiveAmount();

            typeArr[0] = ApplicationConstants.STOCK_QUOTETYPE_BUY;
            typeArr[1] = ApplicationConstants.STOCK_QUOTETYPE_BUY;;
            typeArr[2] = ApplicationConstants.STOCK_QUOTETYPE_BUY;;
            typeArr[3] = ApplicationConstants.STOCK_QUOTETYPE_BUY;;
            typeArr[4] = ApplicationConstants.STOCK_QUOTETYPE_BUY;;
            typeArr[5] = ApplicationConstants.STOCK_QUOTETYPE_SELL;
            typeArr[6] = ApplicationConstants.STOCK_QUOTETYPE_SELL;
            typeArr[7] = ApplicationConstants.STOCK_QUOTETYPE_SELL;
            typeArr[8] = ApplicationConstants.STOCK_QUOTETYPE_SELL;
            typeArr[9] = ApplicationConstants.STOCK_QUOTETYPE_SELL;

            for (int i = 0; i< priceArr.length; i++) {
                if (amountArr[i]==0||priceArr[i]==null||typeArr[i]==0) continue;
                SimulationQuoteDTO s = new SimulationQuoteDTO();
                s.setStockId(stStock.getStockId());
                s.setQuotePrice(priceArr[i]);
                s.setAmount(amountArr[i]);
                s.setQuoteType(typeArr[i]);
                s.setDateTime(System.currentTimeMillis());
                simulationQuoteDTOList.add(s);
            }
        }
        // 模拟订单
        cacheService.setObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, simulationQuoteDTOList);
        cacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, stStockCacheList, 60*5);

        return true;
    }
}