package com.ryd.business.service;

import com.ryd.business.dto.SearchTradeRecordDTO;
import com.ryd.business.model.StTradeRecord;

import java.util.List;

/**
 * <p>标题:交易记录Service</p>
 * <p>描述:依赖于账户、股票配置</p>
 * 包名：com.ryd.business.service
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public interface StTradeRecordService {
    /**
     * 记录交易记录
     * @param tradeRecordList
     * @return
     */
    public boolean saveTradeRecordBatch(List<StTradeRecord> tradeRecordList);

    /**
     * 股票交易
     */
    public void updateStockTrading();

    /**
     * 股票结算
     */
    public void updateStockSettling();

    /**
     * 查询单只股票的交易记录
     * @param searchTradeRecordDTO
     * @return
     */
    public List<StTradeRecord> findTradeRecordListByStock(SearchTradeRecordDTO searchTradeRecordDTO);

    /**
     * 查询交易记录，接收参数
     * @return
     */
    public List<StTradeRecord> findTradeRecordList(SearchTradeRecordDTO searchTradeRecordDTO);
}
