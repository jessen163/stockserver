package com.ryd.business.dao.impl;

import com.bugull.mongo.BuguDao;
import com.mongodb.WriteResult;
import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.dao.StStockDao;
import com.ryd.business.model.StStock;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:股票DaoImpl</p>
 * <p>描述:股票DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:04
 */
@Repository
public class StStockDaoImpl extends BuguDao<StStock> implements StStockDao {
    public StStockDaoImpl() {
        super(StStock.class);
    }

    @Override
    public int add(StStock obj) {
        WriteResult result = super.save(obj);
        return result.getN();
    }

    @Override
    public int update(StStock obj) {
        WriteResult result = super.save(obj);
        return result.getN();
    }

    @Override
    public StStock getTById(String id) {
        return super.findOne(id);
    }

    @Override
    public List<StStock> getTList(StStock obj, int limit, int offset) {
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
    public boolean saveStockBatch(List<StStock> stStockList) {
        WriteResult result = super.insert(stStockList);
        return result.getN() == stStockList.size();
    }
}
