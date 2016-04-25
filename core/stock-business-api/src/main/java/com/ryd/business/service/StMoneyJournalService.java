package com.ryd.business.service;

import com.ryd.business.dto.SearchMoneyJournalDTO;
import com.ryd.business.dto.SearchPositionDTO;
import com.ryd.business.model.StMoneyJournal;

import java.util.List;

/**
 * <p>标题:资金流水Service</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public interface StMoneyJournalService {
    /**
     * 保存资金流水--------------交易完成时调用
     * @param moneyJournalList
     * @return
     */
    public boolean saveMoneyJournalList(List<StMoneyJournal> moneyJournalList);

    /**
     * 查询资金流水
     * @param searchMoneyJournalDTO
     * @return
     */
    public List<StMoneyJournal> findMoneyJournalList(SearchMoneyJournalDTO searchMoneyJournalDTO);
}
