package com.ryd.basecommon.dao;

import java.util.List;

/**
 * 数据库基础操作接口
 *
 * Created by Administrator on 2016/4/22.
 */
public interface BaseDao<T> {

    /**
     * 添加
     * @param obj
     * @return
     */
    public int add(T obj);

    /**
     * 修改信息
     * @param obj
     * @return
     */
    public int update(T obj);

    /**
     * 根据ID查询
     * @param obj
     * @return
     */
    public T getTById(T obj);

    /**
     * 根据条件查询
     * @param obj
     * @param limit
     * @param offset
     * @return
     */
    public List<T> getTList(T obj, int limit,int offset);

    /**
     * 删除帐户
     * @param obj
     * @return
     */
    public int deleteTById(T obj);
}
