package com.ryd.business.mybatis;

import com.ryd.business.model.StSettleRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StSettleRecordMapper {

    int deleteByPrimaryKey(String id);

    int insert(StSettleRecord record);

    int insertSelective(StSettleRecord record);

    StSettleRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StSettleRecord record);

    int updateByPrimaryKey(StSettleRecord record);

    List<StSettleRecord> selectListByKeySelective(@Param(value = "record") StSettleRecord record,
                                                 @Param(value = "limit") Integer limit,
                                                 @Param(value = "offset") Integer offset);
}