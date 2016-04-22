package com.ryd.system.mybatis;

import com.ryd.system.model.StOperateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StOperateLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(StOperateLog record);

    int insertSelective(StOperateLog record);

    StOperateLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StOperateLog record);

    int updateByPrimaryKey(StOperateLog record);

    List<StOperateLog> selectListByKeySelective(@Param(value = "record") StOperateLog record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}