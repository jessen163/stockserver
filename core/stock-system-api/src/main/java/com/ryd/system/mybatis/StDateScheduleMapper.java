package com.ryd.system.mybatis;

import com.ryd.system.model.StDateSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StDateScheduleMapper {
    int deleteByPrimaryKey(String id);

    int insert(StDateSchedule record);

    int insertSelective(StDateSchedule record);

    StDateSchedule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StDateSchedule record);

    int updateByPrimaryKey(StDateSchedule record);

    StDateSchedule selectByDateKey(Date festivalDate);

    List<StDateSchedule> selectListByKeySelective(@Param(value = "record") StDateSchedule record,
                                              @Param(value = "limit") Integer limit,
                                              @Param(value = "offset") Integer offset);
}