package com.ryd.business.mybatis;

import com.ryd.business.model.StTradeRecord;

public interface StTradeRecordMapper {
    int insert(StTradeRecord record);

    int insertSelective(StTradeRecord record);
}