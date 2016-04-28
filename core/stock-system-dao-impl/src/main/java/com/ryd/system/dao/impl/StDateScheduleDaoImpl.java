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
    public StDateSchedule getTById(String id) {
        if(StringUtils.isBlank(id)){
            return null;
        }
        return stDateScheduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StDateSchedule> getTList(StDateSchedule obj, Long startTime, Long endTime, int limit, int offset) {
        if(obj == null){
            obj = new StDateSchedule();
        }
        return stDateScheduleMapper.selectListByKeySelective(obj,limit,offset);
    }

    @Override
    public int deleteTById(String id) {
        if(StringUtils.isBlank(id)){
            return -1;
        }
        return stDateScheduleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public boolean getScheduleByDate(Date festivalDate) {
        StDateSchedule stt = stDateScheduleMapper.selectByDateKey(festivalDate);
        if(stt==null){
            return false;
        }
        return true;
    }
}
