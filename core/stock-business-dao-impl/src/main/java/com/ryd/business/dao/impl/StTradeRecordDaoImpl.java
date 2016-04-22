package com.ryd.business.dao.impl;

import com.ryd.business.dao.StTradeRecordDao;
import com.ryd.business.model.StTradeRecord;
import com.ryd.business.mybatis.StTradeRecordMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>标题:交易记录DaoImpl</p>
 * <p>描述:交易记录DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:06
 */
public class StTradeRecordDaoImpl implements StTradeRecordDao {

    @Autowired
    private StTradeRecordMapper stTradeRecordMapper;

    @Override
    public int add(StTradeRecord obj) {
        return stTradeRecordMapper.insert(obj);
    }

    @Override
    public int update(StTradeRecord obj) {
        return 0;
    }

    @Override
    public StTradeRecord getTById(StTradeRecord obj) {
        if(StringUtils.isBlank(obj.getRecordId())){
            return null;
        }
        return stTradeRecordMapper.selectByPrimaryKey(obj.getRecordId());
    }

    @Override
    public List<StTradeRecord> getTList(StTradeRecord obj, int limit, int offset) {

        if(obj==null){
            obj = new StTradeRecord();
        }
        return stTradeRecordMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(StTradeRecord obj) {
        if(StringUtils.isBlank(obj.getRecordId())){
            return -1;
        }
        return stTradeRecordMapper.deleteByPrimaryKey(obj.getRecordId());
    }
}
