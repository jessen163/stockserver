package com.ryd.business.service.impl;

import com.ryd.business.dto.SearchTradeRecordDTO;
import com.ryd.business.model.StTradeRecord;
import com.ryd.business.service.StTradeRecordService;

import java.util.List;


/**
 * <p>标题:交易记录业务实现类</p>
 * <p>描述:交易记录业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
public class StTradeRecordServiceImpl implements StTradeRecordService {
    @Override
    public boolean saveTradeRecordBatch(List<StTradeRecord> tradeRecordList) {
        return false;
    }

    @Override
    public void updateStockTrading() {
        // 启动交易线程
        // 检查交易时间
        // 从队列获取买卖报价
        // 匹配买卖报价成功，记录交易日志，更新买、卖双方账户信息，更新仓位信息
        // 记录资金流水，更新双方报价信息

        // 操作过程中（每分钟）停止交易，获取股票信息（调用股票更新方法），生成马甲订单，完成后重新启动交易方法
    }

    @Override
    public void updateStockSettling() {
        // 结算
    }

    @Override
    public List<StTradeRecord> findTradeRecordListByStock(SearchTradeRecordDTO searchTradeRecordDTO) {
        return null;
    }

    @Override
    public List<StTradeRecord> findTradeRecordList(SearchTradeRecordDTO searchTradeRecordDTO) {
        return null;
    }
}
