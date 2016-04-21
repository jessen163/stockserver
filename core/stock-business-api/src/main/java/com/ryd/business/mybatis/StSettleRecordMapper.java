package com.ryd.business.mybatis;

import com.ryd.business.model.StSettleRecord;

public interface StSettleRecordMapper {
    int insert(StSettleRecord record);

    int insertSelective(StSettleRecord record);
}