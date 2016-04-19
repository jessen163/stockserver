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
//					// 当前时间为m分钟0秒，暂停业务，同步股票信息，生成马甲订单
//					if (System.currentTimeMillis()/1000%60==0) {
//						stockAnalysisServiceI.quotePriceBySimulation();
//					}
//					logger.info("股票交易中-----------------------------------");
					StTradeQueue stTradeQueueMap = DataConstant.stTradeQueueMap.get(s);
					if (stTradeQueueMap.buyList.isEmpty() || stTradeQueueMap.sellList.isEmpty()) continue;

					boolean sellFlag = true;
					Long buyerKey = Long.MIN_VALUE;
					Long sellerKey = 0L;
					while (sellFlag) {
						// 当前时间为m分钟0秒，暂停业务，同步股票信息，生成马甲订单
						if (System.currentTimeMillis()/1000%60==0) {
							stockAnalysisServiceI.quotePriceBySimulation();
						}
						// 获取第一条卖家报价
						StQuote sellQuote = stTradeQueueMap.getStQuote(sellerKey, Constant.STOCK_STQUOTE_TYPE_SELL);
						if (sellQuote==null) {
							sellFlag = false;
							break;
						}
						// 获取第一条买家报价
						StQuote buyQuote = stTradeQueueMap.getStQuote(buyerKey, Constant.STOCK_STQUOTE_TYPE_BUY);
						if (buyQuote==null) {
							sellFlag = false;
							break;
						}

						// 如果撮合成功, 执行交易, 同时更新买入、卖出队列
						if (ArithUtil.compare(buyQuote.getQuotePrice(), sellQuote.getQuotePrice()) >= 0 && !buyQuote.getAccountId().equals(sellQuote.getAccountId())) {
							//打印队列
							DataInitTool.printTradeQueue("trade before",buyQuote.getStockId());
							//股票
							StStock sts = DataConstant.stockTable.get(buyQuote.getStockId());
							//交易
							stockAnalysisServiceI.dealTrading(stTradeQueueMap, buyQuote, sellQuote, sts);
							//打印队列
							DataInitTool.printTradeQueue("trade end",buyQuote.getStockId());
						} else {
							sellFlag = false;
							break;
						}
					}

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