package com.ryd.business.dao.impl;

import com.ryd.business.dao.StSettleRecordDao;
import com.ryd.business.model.StSettleRecord;
import com.ryd.business.mybatis.StSettleRecordMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:结算记录DaoImpl</p>
 * <p>描述:结算记录DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 13:32
 */
@Repository
public class StSettleRecordDaoImpl implements StSettleRecordDao {

    @Autowired
    private StSettleRecordMapper stSettleRecordMapper;

    @Override
    public int add(StSettleRecord obj) {
        return stSettleRecordMapper.insert(obj);
    }

    @Override
    public int update(StSettleRecord obj) {
        if(StringUtils.isBlank(obj.getSettleRecordId())){
            return -1;
        }
        return stSettleRecordMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StSettleRecord getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stSettleRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StSettleRecord> getTList(StSettleRecord obj, Long startTime,Long endTime, int limit, int offset) {

        if(obj==null){
            obj = new StSettleRecord();
        }
        return stSettleRecordMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stSettleRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addBatch(List<StSettleRecord> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stSettleRecordMapper.insertBatch(list);
        }
        return -1;
    }

    @Override
    public int updateBatch(List<StSettleRecord> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stSettleRecordMapper.updateBatch(list);
        }
        return -1;
    }

    @Override
    public int deleteBatch(List<String> list) {
        if(CollectionUtils.isNotEmpty(list)){
            return stSettleRecordMapper.deleteBatch(list);
        }
        return -1;
    }
}
