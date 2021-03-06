package com.ryd.business.dao.impl;

import com.ryd.business.dao.StStockConfigDao;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.mybatis.StStockConfigMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:股票配置DaoImpl</p>
 * <p>描述:股票配置DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 13:29
 */
@Repository
public class StStockConfigDaoImpl implements StStockConfigDao {

    @Autowired
    private StStockConfigMapper stStockConfigMapper;

    @Override
    public int add(StStockConfig obj) {

        return stStockConfigMapper.insert(obj);
    }

    @Override
    public int update(StStockConfig obj) {
        if(obj.getId()==null || obj.getId().equals("")){
            return -1;
        }
        return stStockConfigMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StStockConfig getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stStockConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StStockConfig> getTList(StStockConfig obj, Long startTime,Long endTime, int limit, int offset) {

        if(obj==null){
            obj = new StStockConfig();
        }
        return stStockConfigMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stStockConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addBatch(List<StStockConfig> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stStockConfigMapper.insertBatch(list);
        }
        return -1;
    }
}
