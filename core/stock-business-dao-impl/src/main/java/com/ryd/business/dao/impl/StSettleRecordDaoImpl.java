package com.ryd.business.dao.impl;

import com.ryd.business.dao.StSettleRecordDao;
import com.ryd.business.model.StSettleRecord;
import com.ryd.business.mybatis.StSettleRecordMapper;
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
    public StSettleRecord getTById(StSettleRecord obj) {
        if(StringUtils.isBlank(obj.getSettleRecordId())){
            return null;
        }
        return stSettleRecordMapper.selectByPrimaryKey(obj.getSettleRecordId());
    }

    @Override
    public List<StSettleRecord> getTList(StSettleRecord obj, int limit, int offset) {

        if(obj==null){
            obj = new StSettleRecord();
        }
        return stSettleRecordMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(StSettleRecord obj) {
        if(StringUtils.isBlank(obj.getSettleRecordId())){
            return -1;
        }
        return stSettleRecordMapper.deleteByPrimaryKey(obj.getSettleRecordId());
    }
}
