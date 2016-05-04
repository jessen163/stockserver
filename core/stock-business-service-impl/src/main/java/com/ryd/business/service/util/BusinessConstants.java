package com.ryd.business.service.util;

import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.dto.StTradeQueueDTO;
import com.ryd.business.model.StStock;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务常量
 * Created by chenji on 2016/5/3.
 */
public class BusinessConstants {
    public static ConcurrentHashMap<String, List<SimulationQuoteDTO>> simulateQuoteMap = new ConcurrentHashMap();
    public static ConcurrentHashMap<String, List<StStock>> stockPriceMap = new ConcurrentHashMap<String, List<StStock>>();
    public static ConcurrentHashMap<String, List<StStock>> tempStockPriceMap = new ConcurrentHashMap<String, List<StStock>>();

    //队列-多只股票
    public static ConcurrentHashMap<String,StTradeQueueDTO> stTradeQueueMap = new ConcurrentHashMap<String,StTradeQueueDTO>();
}
