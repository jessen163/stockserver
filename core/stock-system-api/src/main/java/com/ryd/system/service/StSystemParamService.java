package com.ryd.system.service;

import com.ryd.system.model.StSystemParam;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.system.service
 * 创建人：songby
 * 创建时间：2016/4/22 16:33
 */
public interface StSystemParamService {


    /**
     * 添加参数
     * @param param
     * @return
     */
    public boolean addParam(StSystemParam param);

    /**
     * 修改参数
     * @param param
     * @return
     */
    public boolean updateParam(StSystemParam param);

    /**
     * 删除参数
     * @param param
     * @return
     */
    public boolean deleteParam(StSystemParam param);

    /**
     * 根据key查询参数
     * @param key
     * @return
     */
    public String getParamByKey(String key);

    /**
     * 查询参数列表
     * @param param
     * @param pageIndex
     * @param limit
     * @return
     */
    public List<StSystemParam> getParamList(StSystemParam param, int pageIndex, int limit);

}
