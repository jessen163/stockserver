package com.ryd.system.dao.impl;

import com.ryd.system.dao.StDateScheduleDao;
import com.ryd.system.model.StDateSchedule;
import org.springframework.stereotype.Repository;

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
    @Override
    public int add(StDateSchedule obj) {
        return 0;
    }

    @Override
    public int update(StDateSchedule obj) {
        return 0;
    }

    @Override
    public StDateSchedule getTById(StDateSchedule obj) {
        return null;
    }

    @Override
    public List<StDateSchedule> getTList(StDateSchedule obj, int limit, int offset) {
        return null;
    }

    @Override
    public int deleteTById(StDateSchedule obj) {
        return 0;
    }
}
