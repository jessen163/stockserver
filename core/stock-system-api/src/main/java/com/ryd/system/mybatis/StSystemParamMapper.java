package com.ryd.system.mybatis;

import com.ryd.system.model.StSystemParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StSystemParamMapper {
    int deleteByPrimaryKey(String id);

    int insert(StSystemParam record);

    int insertSelective(StSystemParam record);

    StSystemParam selectByPrimaryKey(String id);

    String selectByKey(String key);

    int updateByPrimaryKeySelective(StSystemParam record);

    int updateByPrimaryKey(StSystemParam record);

    List<StSystemParam> selectListByKeySelective(@Param(value = "record") StSystemParam record,
                                                @Param(value = "limit") Integer limit,
                                                @Param(value = "offset") Integer offset);
}