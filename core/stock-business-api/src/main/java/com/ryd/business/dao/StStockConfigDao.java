package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StStockConfig;

import java.util.List;

/**
 * <p>标题:股票配置Dao</p>
 * <p>描述:股票配置Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/21 17:12
 */
public interface StStockConfigDao extends BaseDao<StStockConfig>{

    public int addBatch(List<StStockConfig> list);
}
