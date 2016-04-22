package com.ryd.business.mybatis;

import com.ryd.business.model.StTradeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StTradeRecordMapper {

    int deleteByPrimaryKey(String id);

    int insert(StTradeRecord record);

    int insertSelective(StTradeRecord record);

    StTradeRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StTradeRecord record);

    int updateByPrimaryKey(StTradeRecord record);

    List<StTradeRecord> selectListByKeySelective(@Param(value = "record") StTradeRecord record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}