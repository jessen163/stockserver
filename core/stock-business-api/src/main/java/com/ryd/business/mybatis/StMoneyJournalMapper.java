package com.ryd.business.mybatis;

import com.ryd.business.model.StMoneyJournal;

public interface StMoneyJournalMapper {
    int insert(StMoneyJournal record);

    int insertSelective(StMoneyJournal record);
}