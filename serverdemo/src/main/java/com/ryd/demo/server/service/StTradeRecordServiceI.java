package com.ryd.demo.server.service;

import com.ryd.demo.server.bean.StTradeRecord;

import java.util.List;

/**
 * <p>标题:股票交易记录ServiceI</p>
 * <p>描述:股票交易记录ServiceI</p>
 * 包名：com.ryd.stockanalysis.service.impl
 * 创建人：songby
 * 创建时间：2016/3/30 13:36
 */
public interface StTradeRecordServiceI {

    /**
     * 添加交易记录
     * @param record
     * @return
     */
    public StTradeRecord addStTradeRecord(StTradeRecord record);


    /**
     * 获取交易记录
     * @param limit
     * @return
     */
    public List<StTradeRecord> getStTradeRecordList(int limit);
}
