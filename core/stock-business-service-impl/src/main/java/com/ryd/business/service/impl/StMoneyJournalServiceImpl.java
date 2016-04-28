package com.ryd.business.service.impl;

import com.ryd.business.dao.StMoneyJournalDao;
import com.ryd.business.dto.SearchMoneyJournalDTO;
import com.ryd.business.model.StMoneyJournal;
import com.ryd.business.service.StMoneyJournalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：songby
 * 创建时间：2016/4/27 18:00
 */
public class StMoneyJournalServiceImpl implements StMoneyJournalService {

    @Autowired
    private StMoneyJournalDao stMoneyJournalDao;

    @Override
    public boolean saveMoneyJournalList(List<StMoneyJournal> moneyJournalList) {
        return stMoneyJournalDao.addBatch(moneyJournalList) > 0;
    }

    @Override
    public List<StMoneyJournal> findMoneyJournalList(SearchMoneyJournalDTO searchMoneyJournalDTO) {

        StMoneyJournal moneyJournal = new StMoneyJournal();
        moneyJournal.setAccountId(searchMoneyJournalDTO.getAccountId());
        moneyJournal.setStockId(searchMoneyJournalDTO.getStockId());
        moneyJournal.setDealType(searchMoneyJournalDTO.getDealType());

        Long startTime = searchMoneyJournalDTO.getStartDate().getTime();
        Long endTime = searchMoneyJournalDTO.getEndDate().getTime();
        return stMoneyJournalDao.getTList(moneyJournal, startTime, endTime, searchMoneyJournalDTO.getLimit(), searchMoneyJournalDTO.getOffset());
    }
}
