package com.ryd.business.service.impl;

import com.ryd.business.dao.StStockConfigDao;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.StStockConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>标题:股票配置业务实现类</p>
 * <p>描述:股票配置业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
public class StStockConfigServiceImpl implements StStockConfigService {
    @Autowired
    private StStockConfigDao stStockConfigDao;

    @Override
    public List<StStockConfig> findStockConfig(StStockConfig stStockConfig, int pageIndex, int limit) {
        // TODO 放入缓存
        int offset = (pageIndex-1)*limit;
        List<StStockConfig> stStockConfigList = stStockConfigDao.getTList(stStockConfig, limit, offset);
        return stStockConfigList;
    }

    @Override
    public StStockConfig findStockConfigById(StStockConfig stStockConfig) {
        // TODO 从缓存获取
        return stStockConfigDao.getTById(stStockConfig);
    }
}
