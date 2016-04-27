package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StTradeRecord;

import java.util.List;

/**
 * <p>标题:交易记录Dao</p>
 * <p>描述:交易记录Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/22 13:53
 */
public interface StTradeRecordDao extends BaseDao<StTradeRecord> {

    public int addBatch(List<StTradeRecord> list);

    public int updateBatch(List<StTradeRecord> list);

    public int deleteBatch(List<String> list);
}
