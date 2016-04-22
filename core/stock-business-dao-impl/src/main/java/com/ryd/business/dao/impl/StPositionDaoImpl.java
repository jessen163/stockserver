package com.ryd.business.dao.impl;

import com.ryd.business.dao.StPositionDao;
import com.ryd.business.model.StMoneyJournal;
import com.ryd.business.model.StPosition;
import com.ryd.business.mybatis.StPositionMapper;
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
    public StPosition getTById(StPosition obj) {
        if(StringUtils.isBlank(obj.getPositionId())){
            return null;
        }
        return stPositionMapper.selectByPrimaryKey(obj.getPositionId());
    }

    @Override
    public List<StPosition> getTList(StPosition obj, int limit, int offset) {

        if(obj==null){
            obj = new StPosition();
        }
        return stPositionMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(StPosition obj) {
        if(StringUtils.isBlank(obj.getPositionId())){
            return -1;
        }
        return stPositionMapper.deleteByPrimaryKey(obj.getPositionId());
    }
}
