package com.ryd.system.dao.impl;

import com.ryd.system.dao.StSystemParamDao;
import com.ryd.system.model.StSystemParam;
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
public class StSystemParamDaoImpl implements StSystemParamDao {
    @Override
    public int add(StSystemParam obj) {
        return 0;
    }

    @Override
    public int update(StSystemParam obj) {
        return 0;
    }

    @Override
    public StSystemParam getTById(StSystemParam obj) {
        return null;
    }

    @Override
    public List<StSystemParam> getTList(StSystemParam obj, int limit, int offset) {
        return null;
    }

    @Override
    public int deleteTById(StSystemParam obj) {
        return 0;
    }
}
