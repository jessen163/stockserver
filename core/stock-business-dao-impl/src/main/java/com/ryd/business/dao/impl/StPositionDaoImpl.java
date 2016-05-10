package com.ryd.business.dao.impl;

import com.ryd.business.dao.StPositionDao;
import com.ryd.business.model.StMoneyJournal;
import com.ryd.business.model.StPosition;
import com.ryd.business.mybatis.StPositionMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:持仓DaoImpl</p>
 * <p>描述:持仓DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:02
 */
@Repository
public class StPositionDaoImpl implements StPositionDao {

    @Autowired
    private StPositionMapper stPositionMapper;

    @Override
    public int add(StPosition obj) {

        return stPositionMapper.insert(obj);
    }

    @Override
    public int update(StPosition obj) {
        if(StringUtils.isBlank(obj.getPositionId())){
            return -1;
        }
        return stPositionMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StPosition getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stPositionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StPosition> getTList(StPosition obj, Long startTime,Long endTime, int limit, int offset) {

        if(obj==null){
            obj = new StPosition();
        }
        return stPositionMapper.selectListByKeySelective(obj, limit, offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stPositionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public StPosition getPositionByAccountStock(String accountId, String stockId){
        return stPositionMapper.selectByKey(accountId, stockId);
    }

    @Override
    public int getCount() {
        return stPositionMapper.selectCount();
    }

    @Override
    public int addBatch(List<StPosition> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stPositionMapper.insertBatch(list);
        }
        return -1;
    }

    @Override
    public int updateBatch(List<StPosition> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stPositionMapper.updateBatch(list);
        }
        return -1;
    }

    @Override
    public int deleteBatch(List<String> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stPositionMapper.deleteBatch(list);
        }
        return -1;
    }

    @Override
    public List<StPosition> findAmountListNoEqual(int limit, int offset) {
        return stPositionMapper.selectListAmountNoEqual(limit,offset);
    }
}
