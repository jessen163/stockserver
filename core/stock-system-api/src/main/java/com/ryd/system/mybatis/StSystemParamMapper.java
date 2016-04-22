package com.ryd.system.mybatis;

import com.ryd.system.model.StSystemParam;

public interface StSystemParamMapper {
    int insert(StSystemParam record);

    int insertSelective(StSystemParam record);
}