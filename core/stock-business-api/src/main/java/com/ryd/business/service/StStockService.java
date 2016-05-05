package com.ryd.business.service;

import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.dto.StStockDetailDTO;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;

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
     * 批量保存报价信息
     * @param stStockList
     * @return
     */
    public boolean saveStockBatch(List<StStock> stStockList);

    /**
     * 更新股票信息
     * @return
     */
    public boolean executeRealTimeStockInfo();

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
    public StStock findStockListByStock(SearchStockDTO searchStockDTO);

    /**
     * 获取单只股票的信息-分时股价列表、实时资金流入流出列表、当日成交价、成交量
     *
     * @param searchStockDTO
     * @return
     */
    public StStockDetailDTO findStockDetailByStock(SearchStockDTO searchStockDTO);

    /**
     * 从缓存中获取股票价格信息
     * @return
     */
    public boolean findStockListToCache();
}
