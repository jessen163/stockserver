package com.ryd.business.mybatis;

import com.ryd.business.model.StMoneyJournal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StMoneyJournalMapper {

    int deleteByPrimaryKey(String id);

    int deleteBatch(List<String> list);

    int insert(StMoneyJournal record);

    int insertSelective(StMoneyJournal record);

    int insertBatch(List<StMoneyJournal> list);

    StMoneyJournal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StMoneyJournal record);

    int updateByPrimaryKey(StMoneyJournal record);

    int updateBatch(List<StMoneyJournal> list);

    int updateBatchSelective(List<StMoneyJournal> list);

    List<StMoneyJournal> selectListByKeySelective(@Param(value = "record") StMoneyJournal record,
                                                  @Param(value = "startTime") Long startTime,
                                                  @Param(value = "endTime") Long endTime,
                                                  @Param(value = "limit") Integer limit,
                                                  @Param(value = "offset") Integer offset);
}