package com.ryd.business.dao.impl;

import com.ryd.business.dao.StMoneyJournalDao;
import com.ryd.business.model.StMoneyJournal;
import com.ryd.business.mybatis.StMoneyJournalMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:资金流水DaoImpl</p>
 * <p>描述:资金流水DaoImpl</p>
 * 包名：com.ryd.business.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 14:00
 */
@Repository
public class StMoneyJournalDaoImpl implements StMoneyJournalDao {

    @Autowired
    private StMoneyJournalMapper stMoneyJournalMapper;

    @Override
    public int add(StMoneyJournal obj) {

        return stMoneyJournalMapper.insert(obj);
    }

    @Override
    public int update(StMoneyJournal obj) {
        if(StringUtils.isBlank(obj.getRecordId())){
            return -1;
        }
        return stMoneyJournalMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StMoneyJournal getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stMoneyJournalMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StMoneyJournal> getTList(StMoneyJournal obj, int limit, int offset) {

        if(obj==null){
            obj = new StMoneyJournal();
        }
        return stMoneyJournalMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stMoneyJournalMapper.deleteByPrimaryKey(id);
    }
}
