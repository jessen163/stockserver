package com.ryd.system.mybatis;

import com.ryd.system.model.StOperateLog;

public interface StOperateLogMapper {
    int insert(StOperateLog record);

    int insertSelective(StOperateLog record);
}