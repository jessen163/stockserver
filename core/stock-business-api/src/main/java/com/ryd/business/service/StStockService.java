package com.ryd.business.service;

import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.model.StStock;

import java.util.List;

/**
 * <p>标题:股票Service--基础业务</p>
 * <p>描述:股票Service</p>
 * 包名：com.ryd.business.service
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public interface StStockService {
    /**
     * 更新股票信息
     * @return
     */
    public boolean updateRealTimeStockInfo();

    /**
     * 获取股票信息
     * @return
     */
    public List<StStock> findStockList(SearchStockDTO searchStockDTO);

    /**
     * 获取单只股票的信息-分时股价列表、实时资金流入流出列表
     *
     * @param searchStockDTO
     * @return
     */
    public List<StStock> findStockListByStock(SearchStockDTO searchStockDTO);
}
