package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StMoneyJournal;

import java.util.List;

/**
 * <p>标题:资金流水Dao</p>
 * <p>描述:资金流水Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/22 13:52
 */
public interface StMoneyJournalDao extends BaseDao<StMoneyJournal> {

    public int addBatch(List<StMoneyJournal> list);

}
