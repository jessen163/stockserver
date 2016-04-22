package com.ryd.system.service.impl;

import com.ryd.system.dao.StSystemParamDao;
import com.ryd.system.model.StSystemParam;
import com.ryd.system.service.StSystemParamService;
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

    @Override
    public boolean addParam(StSystemParam param) {
        return stSystemParamDao.add(param) > 0;
    }

    @Override
    public boolean updateParam(StSystemParam param) {
        return stSystemParamDao.update(param) > 0;
    }

    @Override
    public boolean deleteParam(String id) {
        StSystemParam param = new StSystemParam();
        param.setId(id);
        return stSystemParamDao.deleteTById(param) > 0;
    }

    @Override
    public String getParamByKey(String key) {
        return stSystemParamDao.getParamByKey(key);
    }

    @Override
    public List<StSystemParam> getParamList(StSystemParam param, int pageIndex, int limit) {
        int offset = (pageIndex - 1)*limit;
        return stSystemParamDao.getTList(param,limit,offset);
    }

}
