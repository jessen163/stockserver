package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.DateUtils;
import com.ryd.basecommon.util.HttpclientUtil;
import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.cache.service.ICacheService;
import org.apache.log4j.Logger;

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
        this.cdOrder = cdOrder;
        this.cdAnswer = cdAnswer;
    }

    @Override
    public void run() {
        logger.info("更新股票实时信息.............start...........");
        try {
            cdOrder.await();
            // 下载股票价格信息
            String stockInfoStr = HttpclientUtil.doGet(ApplicationConstants.STOCK_SERVER_STOCKBASE_URL + stockCodeStr);
            // 下载股票成交量信息
            String stockInfoTurnoverStr = HttpclientUtil.doGet(ApplicationConstants.STOCK_SERVER_STOCKTURNOVER_URL + stockCodeStr);
            // 下载股票成交量信息
            String stockInfoQqServerStr = HttpclientUtil.doGet(ApplicationConstants.STOCK_SERVER_STOCKTURNOVER_QQ_URL + stockCodeStr);

            // 通过字符串转换成股票信息
            List<StStock> stockList = getStockInfoByStr(stockCodeStr, stockInfoStr, stockInfoTurnoverStr);
            // 通过字符串转换成股票信息
            List<StStock> stockQQList = getStockInfoFromQQByStr(stockCodeStr, stockInfoQqServerStr);

//            // 保存股票价格等信息
//            stStockService.saveStockBatch(stockList);
            addSimulationQuote(stockList);
            addSimulationQuote(stockQQList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cdAnswer.countDown();
        }
    }

    /**
     *
     * @param stockCodeStr
     * @param stockListStr
     * @return
     */
    private synchronized List<StStock> getStockInfoFromQQByStr(String stockCodeStr, String stockListStr) {
        List<StStock> stockList = new ArrayList<StStock>();

        String[] stockCodeStrArr = stockCodeStr.split(",");
        String[] stockListStrArr = stockListStr.split(";\\n");
        for (int i = 0; i < stockCodeStrArr.length; i++) {
            StStock stock = getStockFromQqByStr(stockCodeStrArr[i], stockListStrArr[i].trim());
            if (stock==null) {
                System.out.println("异常股票代码：" + stockCodeStrArr[i]);
                continue;
            }
            stockList.add(stock);
        }

        return stockList;
    }

    private synchronized List<StStock> getStockInfoByStr(String stockCodeStr, String stockListStr, String stockInfoTurnoverStr) {
        List<StStock> stockList = new ArrayList<StStock>();

        String[] stockCodeStrArr = stockCodeStr.split(",");
        String[] stockListStrArr = stockListStr.split(";\\n");
        // 更新总成交量、总成交额
        String[] stockInfoTurnoverStrArr = stockInfoTurnoverStr.split(";\\n");
        for (int i = 0; i < stockCodeStrArr.length; i++) {
            StStock stock = getStockByStr(stockCodeStrArr[i], stockListStrArr[i].trim());
            StStock stockTemp = getStockMoreByStr(stockCodeStrArr[i], stockInfoTurnoverStrArr[i].trim());
            if (stockTemp != null) stock = stockTemp;
            if (stock==null) {
                System.out.println("异常股票代码：" + stockCodeStrArr[i]);
                continue;
            }
            stockList.add(stock);
        }

        return stockList;
    }

    private synchronized StStock getStockFromQqByStr(String stockCode, String stockStr) {
        if (stockStr==null) return null;
        String[] s = stockStr.split("\"");
        if (s==null || s.length!=2) return null;
        String[] sk = null;
        StStock sts = null;
        StringBuilder bf = new StringBuilder();
        if (!s[1].equals("")) {
            bf.append(s[1]);
            int n = bf.lastIndexOf("~");
            String string = bf.substring(0, n);
            sk = string.split("~");

            if (sk==null || sk.length<32) return null;
            sts = new StStock();
//            sts.setStockId(stockCode);
            sts.setStockCode(stockCode.substring(2));// 获取股票代码
            sts.setStockName(sk[1]);
            // 【2】股票代码
            sts.setCurrentPrice(Double.parseDouble(sk[3]));
            sts.setBfclosePrice(Double.parseDouble(sk[4]));
            sts.setOpenPrice(Double.parseDouble(sk[5]));
            sts.setTradeTotalAmount(Long.parseLong(sk[6]));

            sts.setSellVol(Integer.parseInt(sk[7]));
            sts.setBuyVol(Integer.parseInt(sk[8]));

            sts.setBuyOneAmount(Long.valueOf(sk[10]));
            sts.setBuyOnePrice(Double.parseDouble(sk[9]));
            sts.setBuyTwoAmount(Long.valueOf(sk[12]));
            sts.setBuyTwoPrice(Double.parseDouble(sk[11]));
            sts.setBuyThreeAmount(Long.valueOf(sk[14]));
            sts.setBuyThreePrice(Double.parseDouble(sk[13]));
            sts.setBuyFourAmount(Long.valueOf(sk[16]));
            sts.setBuyFourPrice(Double.parseDouble(sk[15]));
            sts.setBuyFiveAmount(Long.valueOf(sk[18]));
            sts.setBuyFivePrice(Double.parseDouble(sk[17]));

            sts.setSellOneAmount(Long.valueOf(sk[20]));
            sts.setSellOnePrice(Double.parseDouble(sk[19]));
            sts.setSellTwoAmount(Long.valueOf(sk[22]));
            sts.setSellTwoPrice(Double.parseDouble(sk[21]));
            sts.setSellThreeAmount(Long.valueOf(sk[24]));
            sts.setSellThreePrice(Double.parseDouble(sk[23]));
            sts.setSellFourAmount(Long.valueOf(sk[26]));
            sts.setSellFourPrice(Double.parseDouble(sk[25]));
            sts.setSellFiveAmount(Long.valueOf(sk[28]));
            sts.setSellFivePrice(Double.parseDouble(sk[27]));

            sts.setRecentlyTradeRecord(sk[29]);
            sts.setStockDate(DateUtils.formatStrToDate(String.valueOf(sk[30].substring(0, 6)), DateUtils.DATE_FORMAT));
            sts.setStockTime(String.valueOf(sk[30].substring(7)));
            sts.setRiseFallMoney(Double.valueOf(sk[31]));
            sts.setRiseFallPercent(Double.valueOf(sk[32]));
            sts.setMaxPrice(Double.valueOf(sk[33]));
            sts.setMinPrice(Double.valueOf(sk[34]));
            // [35]
            sts.setTradeTotalAmount(Long.parseLong(sk[36]));
            sts.setTradeTotalMoney(Double.valueOf(sk[37]));
            if (sk[38]!=null&&!sk[38].equals("")) {
                sts.setTurnoverRatio(Double.valueOf(sk[38]));
            }
            if (sk[39]!=null&&!sk[39].equals("")) {
                sts.setPriceEarningRatio(Double.valueOf(sk[39]));
            }

            if (sk[43]!=null&&!sk[43].equals("")) {
                sts.setAmplitude(Double.valueOf(sk[43]));
            }
            if (sk[44]!=null&&!sk[44].equals("")) {
                sts.setCirculationCapitalization(Double.valueOf(sk[44]));
            }

            if (sk[45]!=null&&!sk[45].equals("")) {
                sts.setMarketCapitalization(Double.valueOf(sk[45]));
            }
                if (sk[46]!=null&&!sk[46].equals("")) {
                    sts.setPbRatio(Double.valueOf(sk[46]));
                }
            sts.setRiseUpPrice(Double.valueOf(sk[47]));
            sts.setFallDownPrice(Double.valueOf(sk[48]));

            // 放入缓存 - TODO 放入kafka
            BusinessConstants.tempStockPriceMap.put(sts.getStockCode(), sts);
        }
        return sts;
    }

    private synchronized StStock getStockByStr(String stockCode, String stockStr) {
        if (stockStr==null) return null;
        String[] s = stockStr.split("\"");
        if (s==null || s.length!=2) return null;
        String[] sk = null;
        StStock sts = null;
        StringBuilder bf = new StringBuilder();
        if (!s[1].equals("")) {
            bf.append(s[1]);
            int n = bf.lastIndexOf(",");
            String string = bf.substring(0, n);
            sk = string.split(",");

            if (sk==null || sk.length<32) return null;
            sts = new StStock();
//            sts.setStockId(stockCode);
            sts.setStockCode(stockCode.substring(2));// 获取股票代码
            sts.setStockName(sk[0]);
            sts.setOpenPrice(Double.parseDouble(sk[1]));
            sts.setBfclosePrice(Double.parseDouble(sk[2]));
            sts.setCurrentPrice(Double.parseDouble(sk[3]));
            sts.setMaxPrice(Double.parseDouble(sk[4]));
            sts.setMinPrice(Double.parseDouble(sk[5]));
            sts.setBiddingBuyPrice(Double.parseDouble(sk[6]));
            sts.setBiddingSellPrice(Double.parseDouble(sk[7]));
            sts.setTradeAmount(Long.valueOf(sk[8]));
            sts.setTradeMoney(Double.parseDouble(sk[9]));

            sts.setBuyOneAmount(Long.valueOf(sk[10]));
            sts.setBuyOnePrice(Double.parseDouble(sk[11]));
            sts.setBuyTwoAmount(Long.valueOf(sk[12]));
            sts.setBuyTwoPrice(Double.parseDouble(sk[13]));
            sts.setBuyThreeAmount(Long.valueOf(sk[14]));
            sts.setBuyThreePrice(Double.parseDouble(sk[15]));
            sts.setBuyFourAmount(Long.valueOf(sk[16]));
            sts.setBuyFourPrice(Double.parseDouble(sk[17]));
            sts.setBuyFiveAmount(Long.valueOf(sk[18]));
            sts.setBuyFivePrice(Double.parseDouble(sk[19]));

            sts.setSellOneAmount(Long.valueOf(sk[20]));
            sts.setSellOnePrice(Double.parseDouble(sk[21]));
            sts.setSellTwoAmount(Long.valueOf(sk[22]));
            sts.setSellTwoPrice(Double.parseDouble(sk[23]));
            sts.setSellThreeAmount(Long.valueOf(sk[24]));
            sts.setSellThreePrice(Double.parseDouble(sk[25]));
            sts.setSellFourAmount(Long.valueOf(sk[26]));
            sts.setSellFourPrice(Double.parseDouble(sk[27]));
            sts.setSellFiveAmount(Long.valueOf(sk[28]));
            sts.setSellFivePrice(Double.parseDouble(sk[29]));

            sts.setStockDate(DateUtils.formatStrToDate(sk[30], DateUtils.DATE_FORMAT));
            sts.setStockTime(sk[31]);
            // 放入缓存 - TODO 放入kafka
            BusinessConstants.tempStockPriceMap.put(sts.getStockCode(), sts);
        }
        return sts;
    }

    /**
     * 更新总成交量、总成交额
     * @param stockCode
     * @param stockStr
     * @return
     */
    private synchronized StStock getStockMoreByStr(String stockCode, String stockStr) {
        if (stockStr==null) return null;
        String[] s = stockStr.split("\"");
        if (s==null || s.length!=2) return null;
        String[] sk = null;
        StStock sts = null;
        StringBuilder bf = new StringBuilder();
        if (!s[1].equals("")) {
            bf.append(s[1]);
            int n = bf.lastIndexOf(",");
            String string = bf.substring(0, n);
            sk = string.split(",");

            if (sk==null || sk.length<32) return null;
//            sts = new StStock();
            sts = BusinessConstants.tempStockPriceMap.get(stockCode.substring(2));
            if (sts!=null) {
                sts.setTradeTotalAmount(Long.parseLong(sk[8]));
                sts.setTradeTotalMoney(Double.parseDouble(sk[9]));
                // 放入缓存 - TODO 放入kafka
                BusinessConstants.tempStockPriceMap.put(sts.getStockCode(), sts);
            }
        }
        return sts;
    }

    /**
     * 生成马甲盘,同时将实时价格放入缓存，缓存10分钟
     */
    public synchronized boolean addSimulationQuote(List<StStock> stockList) {
        double[] priceArr = new double[10];
        long[] amountArr = new long[10];
        short[] typeArr = new short[10];
        List<SimulationQuoteDTO> simulationQuoteDTOList = null;
        List<StStock> stStockCacheList = new ArrayList<StStock>();
        for (StStock stStock : stockList) {
            // 模拟订单
            simulationQuoteDTOList = new ArrayList<SimulationQuoteDTO>();
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
                if (amountArr[i]==0||priceArr[i]==0||typeArr[i]==0) continue;
                SimulationQuoteDTO s = new SimulationQuoteDTO();
                s.setStockId(stStock.getStockCode());
                s.setQuotePrice(priceArr[i]);
                s.setAmount(amountArr[i]);
                s.setQuoteType(typeArr[i]);
                s.setDateTime(System.currentTimeMillis());
                simulationQuoteDTOList.add(s);
            }
            // 存储马甲订单到缓存--虚拟机缓存 TODO 待优化 放入kafka队列 memcached
            BusinessConstants.simulateQuoteMap.put(stStock.getStockCode(), simulationQuoteDTOList);
        }
//        Object priceListObj = cacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, null);
//        List<StStock> priceList = null;
//        if (priceListObj!=null) {
//            priceList = (List<StStock>) priceListObj;
//        }
//        priceList.addAll(stStockCacheList);
//        cacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_PRICELIST, priceList, 60 * 5);

        return true;
    }

    public static void main(String[] args) {
        String codeStr = "波导股份,10.360,10.370,10.450,10.620,10.210,10.450,10.460,21216032,222412919.000,4300,10.450,24500,10.440,47600,10.430,40500,10.420,37299,10.410,5900,10.460,22782,10.470,35000,10.480,54400,10.490,145600,10.500,2016-04-29,10:10:11,00";
        String[] codeArr = codeStr.split(",");
        System.out.println("code:"+codeArr.length);
//                var hq_str_sh601218="吉鑫科技,5.130,5.150,5.110,5.150,5.090,5.110,5.120,1993328,10191170.000,132100,5.110,101500,5.100,108300,5.090,170800,5.080,85600,5.070,39400,5.120,30300,5.130,112500,5.140,189855,5.150,114400,5.160,2016-04-29,09:56:21,00";
//        var hq_str_sz002441="众业达,15.88,16.00,16.02,16.14,15.76,16.02,16.03,1073624,17094591.40,11200,16.02,14700,16.01,34600,16.00,700,15.99,11400,15.96,1000,16.03,2700,16.04,10100,16.06,7500,16.07,8600,16.08,2016-04-29,09:56:21,00";
//        var hq_str_sh601299="";
//        var hq_str_sz300238="冠昊生物,38.000,38.350,38.600,38.670,37.920,38.600,38.630,626199,24093238.750,7200,38.600,200,38.510,6200,38.500,300,38.490,2500,38.470,300,38.630,1200,38.650,1100,38.660,2900,38.670,5600,38.680,2016-04-29,09:56:21,00";
//        var hq_str_sz150132="";
//        var hq_str_sh600467="好当家,8.220,8.300,8.210,8.280,8.160,8.210,8.230,2587725,21280110.000,68175,8.210,55200,8.200,21800,8.190,96700,8.180,82800,8.170,13100,8.230,23800,8.240,44500,8.250,44900,8.260,20800,8.270,2016-04-29,09:56:21,00";
//        var hq_str_sh603368="柳州医药,71.450,72.150,71.830,72.400,71.450,71.740,71.850,172750,12429750.000,3300,71.740,900,71.730,2400,71.720,4800,71.710,500,71.700,2800,71.850,200,71.910,1200,71.950,900,71.990,300,72.000,2016-04-29,09:56:21,00";
//        var hq_str_sz002228="合兴包装,17.12,17.20,17.39,17.46,17.02,17.39,17.40,2485525,42949625.05,8400,17.39,27900,17.38,17500,17.37,1500,17.36,6200,17.35,36466,17.40,5500,17.41,3300,17.42,11300,17.43,13500,17.44,2016-04-29,09:56:21,00";
//        var hq_str_sh600071="凤凰光学,24.530,24.460,24.410,24.530,24.260,24.410,24.430,118888,2902524.000,1100,24.410,1900,24.400,3700,24.350,500,24.340,1100,24.330,4500,24.430,500,24.450,2700,24.460,450,24.490,13787,24.500,2016-04-29,09:56:21,00";
//        var hq_str_sz002408="齐翔腾达,12.00,12.09,12.15,12.15,11.91,12.15,12.16,945701,11398848.00,8900,12.15,6300,12.13,2600,12.12,1900,12.11,900,12.10,400,12.16,3700,12.17,11900,12.18,15200,12.19,15900,12.20,2016-04-29,09:56:21,00";

        String codeStrs = "波导股份,10.040,10.200,9.400,10.150,9.250,9.380,9.400,69662758,678776363.000,200,9.380,4500,9.370,6500,9.360,173250,9.350,400,9.340,40900,9.400,100,9.410,1500,9.420,2300,9.430,1100,9.440,2016-05-06,13:57:14,00";
        System.out.println(codeStrs.split(",").length);
        String code2Str = "51~五 粮 液~000858~29.31~30.40~30.45~435315~181976~253338~29.30~745~29.29~69~29.28~126~29.26~52~29.25~79~29.31~243~29.32~14~29.33~25~29.34~1308~29.35~11~15:00:21/29.31/11717/S/34343496/34216|14:57:03/29.44/40/B/117710/33887|14:56:59/29.44/88/S/259203/33882|14:56:54/29.46/42/S/123732/33875|14:56:54/29.46/26/M/76596/33865|14:56:48/29.45/20/S/58905/33855~20160506150451~-1.09~-3.59~30.45~29.31~29.44/423598/1263457434~435315~129780~1.15~16.18~~30.45~29.31~3.75~1112.51~1112.60~2.41~33.44~27.36~";
        for (int i = 1; i <= code2Str.split("~").length;i++) {
            System.out.println(i+":"+code2Str.split("~")[i-1]);
        }
    }

}