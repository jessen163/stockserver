package com.ryd.business.service;

import com.ryd.business.model.StSettleRecord;

import java.util.List;

/**
 * <p>标题:结算Service</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service
 * 创建人：songby
 * 创建时间：2016/4/26 15:19
 */
public interface StSettleRecordService {

    /**
     * 股票结算
     * @return
     * @throws Exception
     */
    public boolean updateStockSettling() throws Exception;

    /**
     * 批量添加结算记录
     * @param stSettleRecords
     * @return
     * @throws Exception
     */
    public boolean addSettleRecorBatch(List<StSettleRecord> stSettleRecords) throws Exception;
}
