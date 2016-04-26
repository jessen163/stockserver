package com.ryd.business.mybatis;

import com.ryd.business.model.StStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StStockMapper {

    int deleteByPrimaryKey(String stockId);

    int insert(StStock record);

    int insertSelective(StStock record);

    StStock selectByPrimaryKey(String stockId);

    int updateByPrimaryKeySelective(StStock record);

    int updateByPrimaryKey(StStock record);

    List<StStock> selectListByKeySelective(@Param(value = "record") StStock record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}