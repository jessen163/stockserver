package com.ryd.business.mybatis;

import com.ryd.business.model.StQuote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StQuoteMapper {

    int deleteByPrimaryKey(String id);

    int deleteBatch(List<String> list);

    int insert(StQuote record);

    int insertSelective(StQuote record);

    int insertBatch(List<StQuote> list);

    StQuote selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StQuote record);

    int updateByPrimaryKey(StQuote record);

    int updateBatch(List<StQuote> list);

    int updateBatchSelective(List<StQuote> list);

    List<StQuote> selectListByKeySelective(@Param(value = "record") StQuote record,
                                           @Param(value = "startTime") Long startTime,
                                           @Param(value = "endTime") Long endTime,
                                           @Param(value = "limit") Integer limit,
                                           @Param(value = "offset") Integer offset);

    List<StQuote> selectListByStatus(@Param(value = "record") StQuote record,
                                     @Param(value = "list") List<Short> statusList,
                                           @Param(value = "startTime") Long startTime,
                                           @Param(value = "endTime") Long endTime,
                                           @Param(value = "limit") Integer limit,
                                           @Param(value = "offset") Integer offset);
}