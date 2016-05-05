package com.ryd.business.service;

import com.ryd.business.model.StStockConfig;

import java.util.List;

/**
 * <p>标题:股票配置Service</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service
 * 创建人：chenji
 * 创建时间：2016/4/24 15:11
 */
public interface StStockConfigService {
    /**
     * 股票基础信息
     * @param stStockConfig
     * @param pageIndex
     * @param limit
     * @return
     */
    public List<StStockConfig> findStockConfig(StStockConfig stStockConfig, int pageIndex, int limit);

    /**
     * 通过id查询股票配置信息
     * @param stStockConfig
     * @return
     */
    public StStockConfig findStockConfigById(StStockConfig stStockConfig);

    /**
     * 通过股票代码查询股票ID
     * @param stockCode
     * @return
     */
    public String getStockIdByStockCode(String stockCode);

    /**
     * 通过股票ID查询股票代码
     * @param stockId
     * @return
     */
    public String getStockCodeByStockId(String stockId);
}