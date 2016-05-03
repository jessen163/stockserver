package com.ryd.system.service.impl;

import com.ryd.basecommon.util.CacheConstant;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.dao.StSystemParamDao;
import com.ryd.system.model.StSystemParam;
import com.ryd.system.service.StSystemParamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.system.service.impl
 * 创建人：songby
 * 创建时间：2016/4/22 16:33
 */
@Service
public class StSystemParamServiceImpl implements StSystemParamService {

    @Autowired
    private StSystemParamDao stSystemParamDao;

    @Autowired
    private ICacheService iCacheService;

    @Override
    public boolean addParam(StSystemParam param) {
        return stSystemParamDao.add(param) > 0;
    }

    @Override
    public boolean updateParam(StSystemParam param) {

        iCacheService.remove(CacheConstant.CACHEKEY_SYSTEM_CONFIG_MAP, param.getKeyName());

        return stSystemParamDao.update(param) > 0;
    }

    @Override
    public boolean deleteParam(StSystemParam param) {

        if(StringUtils.isBlank(param.getKeyName())){
            return false;
        }
        iCacheService.remove(CacheConstant.CACHEKEY_SYSTEM_CONFIG_MAP, param.getKeyName());

        return stSystemParamDao.deleteTById(param.getId()) > 0;
    }

    @Override
    public String getParamByKey(String key) {
        String value = null;
        value = iCacheService.getStringByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_MAP, key,null);
        if(StringUtils.isNotBlank(value)){
            return value;
        }
        value = stSystemParamDao.getParamByKey(key);
        iCacheService.setStringByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_MAP, key, value);

        return value;
    }

    @Override
    public List<StSystemParam> getParamList(StSystemParam param, int pageIndex, int limit) {
        int offset = (pageIndex - 1)*limit;
        return stSystemParamDao.getTList(param,null,null,limit,offset);
    }

}
