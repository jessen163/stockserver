package com.ryd.business.dao.impl;

import com.ryd.business.dao.StStockDao;
import com.ryd.business.model.StStock;
import com.ryd.business.mybatis.StStockMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>标题:股票DaoImpl</p>
 * <p>描述:股票DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:04
 */
public class StStockDaoImpl implements StStockDao {

    @Autowired
    private StStockMapper stStockMapper;

    @Override
    public int add(StStock obj) {
        return stStockMapper.insert(obj);
    }

    @Override
    public int update(StStock obj) {
        return 0;
    }

    @Override
    public StStock getTById(StStock obj) {
        if(StringUtils.isBlank(obj.getStockId())){
            return null;
        }
        return stStockMapper.selectByPrimaryKey(obj.getStockId());
    }

    @Override
    public List<StStock> getTList(StStock obj, int limit, int offset) {

        if(obj==null){
            obj = new StStock();
        }
        return stStockMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(StStock obj) {
        if(StringUtils.isBlank(obj.getStockId())){
            return -1;
        }
        return stStockMapper.deleteByPrimaryKey(obj.getStockId());
    }
}
