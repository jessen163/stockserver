package com.ryd.system.mybatis;

import com.ryd.system.model.StDateSchedule;

public interface StDateScheduleMapper {
    int insert(StDateSchedule record);

    int insertSelective(StDateSchedule record);
}