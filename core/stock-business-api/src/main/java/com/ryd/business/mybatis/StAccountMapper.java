package com.ryd.business.mybatis;

import com.ryd.business.model.StAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StAccountMapper {
    int deleteByPrimaryKey(String id);

    int deleteBatch(List<String> list);

    int insert(StAccount record);

    int insertSelective(StAccount record);

    int insertBatch(List<StAccount> list);

    StAccount selectByPrimaryKey(String id);

    StAccount selectByNameKey(String accountNum);

    int updateByPrimaryKeySelective(StAccount record);

    int updateByPrimaryKey(StAccount record);

    int updateBatch(List<StAccount> list);

    int updateBatchSelective(List<StAccount> list);

    List<StAccount> selectListByKeySelective(@Param(value = "account") StAccount account,
                                             @Param(value = "startTime") Long startTime,
                                             @Param(value = "endTime") Long endTime,
                                             @Param(value = "limit") Integer limit,
                                             @Param(value = "offset") Integer offset);

    StAccount selectByNamePassword(@Param(value = "accountNum") String account_num,
                                   @Param(value = "password") String password);

}