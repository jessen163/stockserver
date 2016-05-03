package com.ryd.business.dao.impl;

import com.ryd.business.dao.StQuoteDao;
import com.ryd.business.model.StQuote;
import com.ryd.business.mybatis.StQuoteMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:报价DaoImpl</p>
 * <p>描述:报价DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:03
 */
@Repository
public class StQuoteDaoImpl implements StQuoteDao {

    @Autowired
    private StQuoteMapper stQuoteMapper;

    @Override
    public int add(StQuote obj) {
        return stQuoteMapper.insert(obj);
    }

    @Override
    public int addBatch(List<StQuote> list) {
        return stQuoteMapper.insertBatch(list);
    }

    @Override
    public int update(StQuote obj) {
        if(StringUtils.isBlank(obj.getQuoteId())){
            return -1;
        }
        return stQuoteMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StQuote getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stQuoteMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StQuote> getTList(StQuote obj, Long startTime,Long endTime, int limit, int offset) {

        if(obj==null){
            obj = new StQuote();
        }
        return stQuoteMapper.selectListByKeySelective(obj, startTime, endTime, limit, offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stQuoteMapper.deleteByPrimaryKey(id);
    }
}
