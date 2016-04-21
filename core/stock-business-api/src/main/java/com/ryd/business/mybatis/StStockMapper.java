package com.ryd.business.mybatis;

import com.ryd.business.model.StStock;

public interface StStockMapper {
    int deleteByPrimaryKey(String stockId);

    int insert(StStock record);

    int insertSelective(StStock record);

    StStock selectByPrimaryKey(String stockId);

    int updateByPrimaryKeySelective(StStock record);

    int updateByPrimaryKey(StStock record);
}