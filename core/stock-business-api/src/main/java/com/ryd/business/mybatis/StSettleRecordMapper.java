package com.ryd.business.mybatis;

import com.ryd.business.model.StSettleRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StSettleRecordMapper {

    int deleteByPrimaryKey(String id);

    int deleteBatch(List<String> list);

    int insert(StSettleRecord record);

    int insertSelective(StSettleRecord record);

    int insertBatch(List<StSettleRecord> list);

    StSettleRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StSettleRecord record);

    int updateByPrimaryKey(StSettleRecord record);

    int updateBatch(List<StSettleRecord> list);

    int updateBatchSelective(List<StSettleRecord> list);

    List<StSettleRecord> selectListByKeySelective(@Param(value = "record") StSettleRecord record,
                                                 @Param(value = "limit") Integer limit,
                                                 @Param(value = "offset") Integer offset);
}