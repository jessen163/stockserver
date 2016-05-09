package com.ryd.business.dao.impl;

import com.bugull.mongo.BuguDao;
import com.mongodb.WriteResult;
import com.ryd.business.dao.StStockHistoryDao;
import com.ryd.business.model.StStockHistory;
import org.apache.commons.lang.StringUtils;
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
public class StStockHistoryDaoImpl extends BuguDao<StStockHistory> implements StStockHistoryDao {
    public StStockHistoryDaoImpl() {
        super(StStockHistory.class);
    }

    @Override
    public int add(StStockHistory obj) {
        WriteResult result = super.save(obj);
        return result.getN();
    }

    @Override
    public int update(StStockHistory obj) {
        WriteResult result = super.save(obj);
        return result.getN();
    }

    @Override
    public StStockHistory getTById(String id) {
        return super.findOne(id);
    }

    @Override
    public List<StStockHistory> getTList(StStockHistory obj, Long startTime,Long endTime, int limit, int offset) {
        int pageNum = limit/offset+1;
        int pageSize = offset;
        return super.findAll(pageNum, pageSize);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
//        return stStockMapper.deleteByPrimaryKey(obj.getStockId());
        WriteResult result = super.remove(id);
        return result.getN();
    }

    @Override
    public boolean saveStockHistoryBatch(List<StStockHistory> stStockHistoryList) {
        WriteResult result = super.insert(stStockHistoryList);
        return result.getN() == stStockHistoryList.size();
    }
}
