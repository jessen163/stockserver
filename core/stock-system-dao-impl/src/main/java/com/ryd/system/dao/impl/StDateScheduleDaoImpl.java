package com.ryd.system.dao.impl;

import com.ryd.system.dao.StDateScheduleDao;
import com.ryd.system.model.StDateSchedule;
import com.ryd.system.mybatis.StDateScheduleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.system.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 15:19
 */
@Repository
public class StDateScheduleDaoImpl implements StDateScheduleDao {

    @Autowired
    private StDateScheduleMapper stDateScheduleMapper;

    @Override
    public int add(StDateSchedule obj) {
        return stDateScheduleMapper.insert(obj);
    }

    @Override
    public int update(StDateSchedule obj) {
        if(StringUtils.isBlank(obj.getId())){
            return -1;
        }
        return stDateScheduleMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public StDateSchedule getTById(StDateSchedule obj) {
        if(StringUtils.isBlank(obj.getId())){
            return null;
        }
        return stDateScheduleMapper.selectByPrimaryKey(obj.getId());
    }

    @Override
    public List<StDateSchedule> getTList(StDateSchedule obj, int limit, int offset) {
        if(obj == null){
            obj = new StDateSchedule();
        }
        return stDateScheduleMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(StDateSchedule obj) {
        if(StringUtils.isBlank(obj.getId())){
            return -1;
        }
        return stDateScheduleMapper.deleteByPrimaryKey(obj.getId());
    }

    @Override
    public List<Date> getScheduleByType(Short type) {
        return stDateScheduleMapper.selectListByKeyType(type);
    }
}
