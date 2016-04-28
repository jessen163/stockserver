package com.ryd.business.dao.impl;

import com.ryd.business.dao.StStockHistoryDao;
import com.ryd.business.model.StStockHistory;
import com.ryd.business.mybatis.StStockHistoryMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:股票历史DaoImpl</p>
 * <p>描述:股票历史DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:05
 */
@Repository
public class StStockHistoryDaoImpl implements StStockHistoryDao {

    @Autowired
    private StStockHistoryMapper stStockHistoryMapper;

    @Override
    public int add(StStockHistory obj) {
        return stStockHistoryMapper.insert(obj);
    }

    @Override
    public int update(StStockHistory obj) {
        return 0;
    }

    @Override
    public StStockHistory getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stStockHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StStockHistory> getTList(StStockHistory obj, Long startTime,Long endTime, int limit, int offset) {

        if(obj==null){
            obj = new StStockHistory();
        }
        return stStockHistoryMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stStockHistoryMapper.deleteByPrimaryKey(id);
    }
}
