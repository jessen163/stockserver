package com.ryd.business.mybatis;

import com.ryd.business.model.StStockHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StStockHistoryMapper {

    int deleteByPrimaryKey(String stockId);

    int deleteBatch(List<String> list);

    int insert(StStockHistory record);

    int insertSelective(StStockHistory record);

    int insertBatch(List<StStockHistory> list);

    StStockHistory selectByPrimaryKey(String stockId);

    int updateByPrimaryKeySelective(StStockHistory record);

    int updateByPrimaryKey(StStockHistory record);

    List<StStockHistory> selectListByKeySelective(@Param(value = "record") StStockHistory record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}