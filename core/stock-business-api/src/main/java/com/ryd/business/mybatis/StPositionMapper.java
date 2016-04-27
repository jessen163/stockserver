package com.ryd.business.mybatis;

import com.ryd.business.model.StPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StPositionMapper {

    int deleteByPrimaryKey(String positionId);

    int deleteBatch(List<String> list);

    int insert(StPosition record);

    int insertSelective(StPosition record);

    int insertBatch(List<StPosition> list);

    StPosition selectByPrimaryKey(String positionId);

    StPosition selectByKey(@Param(value = "accountId") String accountId,
                           @Param(value = "stockId") String stockId);

    int updateByPrimaryKeySelective(StPosition record);

    int updateByPrimaryKey(StPosition record);

    int updateBatch(List<StPosition> list);

    int updateBatchSelective(List<StPosition> list);

    List<StPosition> selectListByKeySelective(@Param(value = "record") StPosition record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}