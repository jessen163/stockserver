package com.ryd.business.mybatis;

import com.ryd.business.model.StStockHistory;

public interface StStockHistoryMapper {
    int deleteByPrimaryKey(String stockId);

    int insert(StStockHistory record);

    int insertSelective(StStockHistory record);

    StStockHistory selectByPrimaryKey(String stockId);

    int updateByPrimaryKeySelective(StStockHistory record);

    int updateByPrimaryKey(StStockHistory record);
}