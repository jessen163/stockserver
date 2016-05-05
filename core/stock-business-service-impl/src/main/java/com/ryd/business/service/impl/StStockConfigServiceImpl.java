package com.ryd.business.service.impl;

import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.dao.StStockConfigDao;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.StStockConfigService;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题:股票配置业务实现类</p>
 * <p>描述:股票配置业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
@Service
public class StStockConfigServiceImpl implements StStockConfigService {
    @Autowired
    private StStockConfigDao stStockConfigDao;
    @Autowired
    private ICacheService iCacheService;

    @Override
    public List<StStockConfig> findStockConfig(StStockConfig stStockConfig, int pageIndex, int limit) {
        List<StStockConfig> stStockConfigList = null;
        if (BusinessConstants.stockCodeStockIdMap==null||BusinessConstants.stockCodeStockIdMap.size()==0) {
            int offset = (pageIndex-1)*limit;
            stStockConfigList = stStockConfigDao.getTList(null, null, null, limit, offset);
            for (StStockConfig stock: stStockConfigList) {
                BusinessConstants.stockConfigMap.put(stock.getId(), stock);
                BusinessConstants.stockCodeStockIdMap.put(stock.getStockCode(), stock.getId());
            }
        } else {
            stStockConfigList = new ArrayList<StStockConfig>(BusinessConstants.stockConfigMap.values());
        }
        /*Object stockConfigListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCKCONFIGLIST+pageIndex+limit, null);
        if (stockConfigListObj!=null) {
            stStockConfigList = (List<StStockConfig>)stockConfigListObj;
        } else {
            int offset = (pageIndex-1)*limit;
            stStockConfigList = stStockConfigDao.getTList(null, null, null, limit, offset);

            if (!StringUtils.isEmpty(stStockConfigList)) {
                iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCKCONFIGLIST+pageIndex+limit, stStockConfigList);
//                Map<String, StStockConfig> stockMap = new HashMap<String, StStockConfig>();
                List<String> stockIdList = new ArrayList<String>();
                for (StStockConfig stock: stStockConfigList) {
//                    stockMap.put(stock.getId(), stock);
                    stockIdList.add(stock.getStockCode() + ":" + stock.getStockTypeName());
                    iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCKCONFIGLIST_MAP, stock.getId(), stock, 60 * 60 * 8);
                    iCacheService.setStringByKey(CacheConstant.CACHEKEY_STOCKCONFIGIDNAME_MAP, stock.getStockCode(), stock.getId(), Integer.MAX_VALUE);
                }
                iCacheService.setObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, stockIdList);
            }
        }*/

        return stStockConfigList;
    }

    @Override
    public StStockConfig findStockConfigById(StStockConfig stStockConfig) {
        Map<String, StStockConfig> stockMap = null;
        Object stockObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCKCONFIGLIST_MAP, null);
        if (stockObj!=null) {
            stockMap = (Map<String, StStockConfig>)stockObj;
        }
        if (stockMap==null||stockMap.get(stStockConfig.getId())==null){
            this.findStockConfig(null, 1, Integer.MAX_VALUE);
            stStockConfig = stStockConfigDao.getTById(stStockConfig.getId());
        }

        return stStockConfig;
    }

    @Override
    public String getStockIdByStockCode(String stockCode) {
        /*String str = iCacheService.getStringByKey(CacheConstant.CACHEKEY_STOCKCONFIGIDNAME_MAP, stockCode, null);
        if (str == null) {
            this.findStockConfig(null, 1, Integer.MAX_VALUE);
        }
        str = iCacheService.getStringByKey(CacheConstant.CACHEKEY_STOCKCONFIGIDNAME_MAP, stockCode, null);
        return str;*/
//        BusinessConstants.stockConfigMap.get()
        String str = BusinessConstants.stockCodeStockIdMap.get(stockCode);
        if (StringUtils.isEmpty(str)) {
            this.findStockConfig(null, 1, Integer.MAX_VALUE);
        }
        return BusinessConstants.stockCodeStockIdMap.get(stockCode);
    }
}
