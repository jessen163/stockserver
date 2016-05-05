package com.ryd.business.service.util;

import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.dto.StTradeQueueDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务常量
 * Created by chenji on 2016/5/3.
 */
public class BusinessConstants {
    // 股票配置信息
    public static ConcurrentHashMap<String, StStockConfig> stockConfigMap = new ConcurrentHashMap<String, StStockConfig>();
    public static ConcurrentHashMap<String, List<SimulationQuoteDTO>> simulateQuoteMap = new ConcurrentHashMap();
    public static ConcurrentHashMap<String, StStock> stockPriceMap = new ConcurrentHashMap<String, StStock>();
    public static ConcurrentHashMap<String, StStock> tempStockPriceMap = new ConcurrentHashMap<String, StStock>();

    //队列-多只股票
    public static ConcurrentHashMap<String,StTradeQueueDTO> stTradeQueueMap = new ConcurrentHashMap<String,StTradeQueueDTO>();
}
