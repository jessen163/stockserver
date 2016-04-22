package com.ryd.business.mybatis;

import com.ryd.business.model.StQuote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StQuoteMapper {
    int deleteByPrimaryKey(String id);

    int insert(StQuote record);

    int insertSelective(StQuote record);

    StQuote selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StQuote record);

    int updateByPrimaryKey(StQuote record);

    List<StQuote> selectListByKeySelective(@Param(value = "record") StQuote record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}