package com.ryd.business.mybatis;

import com.ryd.business.model.StStockConfig;

public interface StStockConfigMapper {
    int insert(StStockConfig record);

    int insertSelective(StStockConfig record);
}