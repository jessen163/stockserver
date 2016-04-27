package com.ryd.business.dao.impl;

import com.ryd.business.dao.StTradeRecordDao;
import com.ryd.business.model.StTradeRecord;
import com.ryd.business.mybatis.StTradeRecordMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>标题:交易记录DaoImpl</p>
 * <p>描述:交易记录DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:06
 */
@Repository
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
    public StTradeRecord getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stTradeRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StTradeRecord> getTList(StTradeRecord obj, int limit, int offset) {

        if(obj==null){
            obj = new StTradeRecord();
        }
        return stTradeRecordMapper.selectListByKeySelective(obj, limit, offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stTradeRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addBatch(List<StTradeRecord> list){
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return stTradeRecordMapper.insertBatch(list);
    }

    @Override
    public int updateBatch(List<StTradeRecord> list){
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return stTradeRecordMapper.updateBatchSelective(list);
    }

    @Override
    public int deleteBatch(List<String> list){
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return stTradeRecordMapper.deleteBatch(list);
    }
}
