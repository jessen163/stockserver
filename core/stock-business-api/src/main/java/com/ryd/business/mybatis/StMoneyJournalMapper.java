package com.ryd.business.mybatis;

import com.ryd.business.model.StMoneyJournal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StMoneyJournalMapper {

    int deleteByPrimaryKey(String id);

    int insert(StMoneyJournal record);

    int insertSelective(StMoneyJournal record);

    StMoneyJournal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StMoneyJournal record);

    int updateByPrimaryKey(StMoneyJournal record);

    List<StMoneyJournal> selectListByKeySelective(@Param(value = "record") StMoneyJournal record,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}