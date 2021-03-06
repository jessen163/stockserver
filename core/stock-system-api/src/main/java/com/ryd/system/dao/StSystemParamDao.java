package com.ryd.system.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.system.model.StSystemParam;

import java.util.List;

/**
 * <p>标题:参数配置Dap</p>
 * <p>描述:参数配置Dao</p>
 * 包名：com.ryd.system.dao
 * 创建人：songby
 * 创建时间：2016/4/22 15:18
 */
public interface StSystemParamDao extends BaseDao<StSystemParam> {

    /**
     * 根据key查询
     * @param key
     * @return
     */
    public String getParamByKey(String key);

    public List<StSystemParam> getParamByKeyType(List<Short> keyTypes);
}
