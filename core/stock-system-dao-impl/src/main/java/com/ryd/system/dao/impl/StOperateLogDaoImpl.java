package com.ryd.system.dao.impl;

import com.ryd.system.dao.StOperateLogDao;
import com.ryd.system.model.StOperateLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.system.dao.impl
 * 创建人：songby
 * 创建时间：2016/4/22 15:20
 */
@Repository
public class StOperateLogDaoImpl implements StOperateLogDao {
    @Override
    public int add(StOperateLog obj) {
        return 0;
    }

    @Override
    public int update(StOperateLog obj) {
        return 0;
    }

    @Override
    public StOperateLog getTById(String id) {
        return null;
    }

    @Override
    public List<StOperateLog> getTList(StOperateLog obj, Long startTime, Long endTime, int limit, int offset) {
        return null;
    }

    @Override
    public int deleteTById(String id) {
        return 0;
    }
}
