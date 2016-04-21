package com.ryd.business.mybatis;

import com.ryd.business.model.StPosition;

public interface StPositionMapper {
    int deleteByPrimaryKey(String positionId);

    int insert(StPosition record);

    int insertSelective(StPosition record);

    StPosition selectByPrimaryKey(String positionId);

    int updateByPrimaryKeySelective(StPosition record);

    int updateByPrimaryKey(StPosition record);
}