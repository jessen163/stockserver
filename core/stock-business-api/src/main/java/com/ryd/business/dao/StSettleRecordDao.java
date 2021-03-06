package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StSettleRecord;

import java.util.List;

/**
 * <p>标题:结算记录Dao</p>
 * <p>描述:结算记录Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/21 17:13
 */
public interface StSettleRecordDao extends BaseDao<StSettleRecord> {

    public int addBatch(List<StSettleRecord> list);

    public int updateBatch(List<StSettleRecord> list);

    public int deleteBatch(List<String> list);
}
