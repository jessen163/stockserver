package com.ryd.demo.server.handle;

import com.ryd.demo.server.bean.StQuote;
import com.ryd.demo.server.common.Constant;
import com.ryd.demo.server.service.StockAnalysisServiceI;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * <p>标题:用户买入/卖出股票线程</p> 
 * <p>描述:传入账户、股票信息，调用买入/卖出股票的报价动作--------------实现</p>
 * 包名：com.ryd.stockanalysis.handle
 *
 * 创   建 人：yl
 * 创建时间：2016-3-29 下午1:25:41
 */
public class StockTradeThread implements Runnable {

	private static Logger logger = Logger.getLogger(StockTradeThread.class);

	StockAnalysisServiceI stockAnalysisServiceI;

	ConcurrentMap<String,Map> quoteTable;

	public StockTradeThread(StockAnalysisServiceI stockAnalysisServiceI,ConcurrentMap<String,Map> quoteTable) {
		this.stockAnalysisServiceI = stockAnalysisServiceI;
		this.quoteTable = quoteTable;
	}

	@Override
	public void run() {
			try {
				if(!quoteTable.isEmpty()) {
					//处理报价
					for (String key : quoteTable.keySet()) {
						Map paramMap = quoteTable.get(key);
						if (paramMap == null) {
							Thread.sleep(1000);
							continue;
						}

						StQuote stQuote = new StQuote();

						stQuote.setStockId(paramMap.get("stockId").toString());
						stQuote.setAccountId(paramMap.get("accountId").toString());
						stQuote.setQuotePrice((Double) paramMap.get("quotePrice"));
						stQuote.setAmount((Integer) paramMap.get("amount"));
						stQuote.setCurrentAmount((Integer) paramMap.get("amount"));
						stQuote.setType((Integer) paramMap.get("type"));
						stQuote.setDateTime(System.currentTimeMillis());
						stQuote.setStatus(Constant.STOCK_STQUOTE_STATUS_TRUSTEE);

						boolean rs = stockAnalysisServiceI.quotePrice(stQuote);

						if(rs) {
							logger.info(paramMap.get("info").toString());
						}
						Thread.sleep(1000);
					}
				}else{
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}