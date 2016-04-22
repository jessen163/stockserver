package com.ryd.business.mybatis;

import com.ryd.business.model.StPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StPositionMapper {

    int deleteByPrimaryKey(String positionId);

    int insert(StPosition record);

    int insertSelective(StPosition record);

    StPosition selectByPrimaryKey(String positionId);

    int updateByPrimaryKeySelective(StPosition record);

    int updateByPrimaryKey(StPosition record);

    List<StPosition> selectListByKeySelective(@Param(value = "record") StPosition record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}