package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.model.StStock;

import java.util.List;

/**
 * <p>标题:股票Dao</p>
 * <p>描述:股票Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/22 13:53
 */
public interface StStockDao extends BaseDao<StStock> {
    /**
     * 批量保存股票信息
     * @param stStockList
     * @return
     */
    public boolean saveStockBatch(List<StStock> stStockList);

    /**
     * 查询股票价格-实时
     * 最新的一条
     * @param searchStockDTO
     * @return
     */
    public List<StStock> findStStockListCurrentTime(SearchStockDTO searchStockDTO);
}
