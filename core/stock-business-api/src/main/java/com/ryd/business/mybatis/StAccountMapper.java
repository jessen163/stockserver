package com.ryd.business.mybatis;

import com.ryd.business.model.StAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(StAccount record);

    int insertSelective(StAccount record);

    StAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StAccount record);

    int updateByPrimaryKey(StAccount record);

    List<StAccount> selectListByKeySelective(@Param(value = "account") StAccount account,
                                             @Param(value = "limit") Integer limit,
                                             @Param(value = "offset") Integer offset);

    StAccount selectByNamePassword(@Param(value = "account_num") String account_num,
                                   @Param(value = "password") String password);
}