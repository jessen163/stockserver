package com.ryd.demo.server.handle;

import com.ryd.demo.server.bean.*;
import com.ryd.demo.server.common.Constant;
import com.ryd.demo.server.common.DataConstant;
import com.ryd.demo.server.common.DataInitTool;
import com.ryd.demo.server.service.*;
import com.ryd.demo.server.service.impl.*;
import com.ryd.demo.server.util.ArithUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * <p>标题:股票交易队列-处理交易</p>
 * <p>描述:股票交易队列-处理交易</p>
 * 包名：com.ryd.stockanalysis
 * 创建人：songby
 * 创建时间：2016/3/28 17:57
 */
public class StTradeThread implements Runnable {

	private static Logger logger = Logger.getLogger(StTradeThread.class);

	private StockAnalysisServiceI stockAnalysisServiceI;
	private StDateScheduleServiceI scheduleServiceI;

	public StTradeThread() {
		stockAnalysisServiceI = new StockAnalysisServiceImpl();
		scheduleServiceI = new StDateScheduleServiceImpl();
	}

	@Override
	public void run(){
//		logger.info("股票交易引擎---------------开始--------------------");
		while (true) {
			//判断时间是否允许交易
			int tstatus = scheduleServiceI.dateAndTimeJudge();
			if(tstatus != Constant.STQUOTE_TRADE_TIMECOMPARE_1){
				try {
					TimeUnit.MINUTES.sleep(1);
					return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			long start = System.currentTimeMillis();
			if (!DataConstant.stTradeQueueMap.isEmpty()) {
				for (String s : DataConstant.stTradeQueueMap.keySet()) {
					long end = System.currentTimeMillis();
					if ((end-start)/1000%60==0) {
						stockAnalysisServiceI.quotePriceBySimulation();
					}
//					logger.info("股票交易中-----------------------------------");
					StTradeQueue stTradeQueueMap = DataConstant.stTradeQueueMap.get(s);
					if (stTradeQueueMap.buyList.isEmpty() || stTradeQueueMap.sellList.isEmpty()) continue;
//							logger.info("stTradeQueueMap: 股票ID"+s+"-------------------其他信息："+stTradeQueueMap);

					boolean sellFlag = true;
					Long buyerKey = Long.MIN_VALUE;
					Long sellerKey = 0L;
//							logger.info("stTradeQueueMap："+stTradeQueueMap);
					while (sellFlag) {
						StQuote sellQuote = stTradeQueueMap.getStQuote(sellerKey, Constant.STOCK_STQUOTE_TYPE_SELL);
						if (sellQuote==null) {
							sellFlag = false;
							sellerKey = 0L;
							break;
						}

//								logger.info("buyQuote: 股票ID"+buyQuote.getStockId()+"-------------------其他信息："+buyQuote);
						boolean buyFlag = true;
						while (buyFlag) {
							StQuote buyQuote = stTradeQueueMap.getStQuote(buyerKey, Constant.STOCK_STQUOTE_TYPE_BUY);
							if (buyQuote==null) {
								buyFlag = false;
								buyerKey = Long.MIN_VALUE;
								sellerKey = sellQuote.getQuotePriceForSort();
								break;
							}

							if (ArithUtil.compare(buyQuote.getQuotePrice(), sellQuote.getQuotePrice()) >= 0 && !buyQuote.getAccountId().equals(sellQuote.getAccountId())) {

								//打印队列
								DataInitTool.printTradeQueue("trade before",buyQuote.getStockId());

								//股票
								StStock sts = DataConstant.stockTable.get(buyQuote.getStockId());
								//交易
								stockAnalysisServiceI.dealTrading(stTradeQueueMap, buyQuote, sellQuote, sts);

								//打印队列
								DataInitTool.printTradeQueue("trade end",buyQuote.getStockId());

								buyFlag = false;
							}

							buyerKey = buyQuote.getQuotePriceForSort();
							sellerKey = sellQuote.getQuotePriceForSort();
						}
					}
//							logger.info("stTradeQueueMap："+stTradeQueueMap.buyList.size()+"---------------"+stTradeQueueMap.sellList.size());

					DataConstant.stTradeQueueMap.put(s, stTradeQueueMap);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
//		logger.info("股票交易引擎---------------结束--------------------");
	}

}
