package com.ryd.business.mybatis;

import com.ryd.business.model.StStockConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StStockConfigMapper {

    int deleteByPrimaryKey(String id);

    int insert(StStockConfig record);

    int insertSelective(StStockConfig record);

    StStockConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StStockConfig record);

    int updateByPrimaryKey(StStockConfig record);

    List<StStockConfig> selectListByKeySelective(@Param(value = "record") StStockConfig record,
                                             @Param(value = "limit") Integer limit,
                                             @Param(value = "offset") Integer offset);
}