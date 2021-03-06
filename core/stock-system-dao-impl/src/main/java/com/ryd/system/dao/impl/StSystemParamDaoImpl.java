package com.ryd.system.dao.impl;

import com.ryd.system.dao.StSystemParamDao;
import com.ryd.system.model.StSystemParam;
import com.ryd.system.mybatis.StSystemParamMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.system.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 15:20
 */
@Repository
public class StSystemParamDaoImpl implements StSystemParamDao {

    @Autowired
    private StSystemParamMapper stSystemParamMapper;

    @Override
    public int add(StSystemParam obj) {
        return stSystemParamMapper.insert(obj);
    }

    @Override
    public int update(StSystemParam obj) {
        if(StringUtils.isBlank(obj.getId())){
            return -1;
        }
        return stSystemParamMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StSystemParam getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stSystemParamMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StSystemParam> getTList(StSystemParam obj, Long startTime, Long endTime, int limit, int offset) {
        if (obj == null){
            obj = new StSystemParam();
        }
        return stSystemParamMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stSystemParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String getParamByKey(String key){
        return stSystemParamMapper.selectByKey(key);
    }

    @Override
    public List<StSystemParam> getParamByKeyType(List<Short> keyTypes) {
        return stSystemParamMapper.selectByKeyTypes(keyTypes);
    }
}
