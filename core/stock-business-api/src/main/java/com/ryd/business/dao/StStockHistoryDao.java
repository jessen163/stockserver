package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StStockHistory;

import java.util.List;

/**
 * <p>标题:股票历史Dao</p>
 * <p>描述:股票历史Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/22 13:53
 */
public interface StStockHistoryDao extends BaseDao<StStockHistory> {
    /**
     * 批量保存股票信息
     * @param stStockHistoryList
     * @return
     */
    public boolean saveStockHistoryBatch(List<StStockHistory> stStockHistoryList);
}
