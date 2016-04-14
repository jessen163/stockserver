package com.ryd.demo.server.service.impl;

import com.ryd.demo.server.bean.StTradeRecord;
import com.ryd.demo.server.common.DataConstant;
import com.ryd.demo.server.service.StTradeRecordServiceI;

import java.util.List;

/**
 * <p>标题:股票交易记录ServiceImpl</p>
 * <p>描述:股票交易记录ServiceImpl</p>
 * 包名：com.ryd.stockanalysis.service.impl
 * 创建人：songby
 * 创建时间：2016/3/30 13:36
 */
public class StTradeRecordServiceImpl implements StTradeRecordServiceI {

    @Override
    public StTradeRecord addStTradeRecord(StTradeRecord record){
        //添加交易记录列表
        DataConstant.recordList.add(record);
        return record;
    }

    @Override
    public List<StTradeRecord> getStTradeRecordList(int limit){

        return null;
    }
}
