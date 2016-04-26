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
     * 批量添加
     * @param list
     * @return
     */
//    public int addBatch(List<T> list);

    /**
     * 修改信息
     * @param obj
     * @return
     */
    public int update(T obj);

    /**
     * 批量修改
     * @param list
     * @return
     */
//    public int updateBatch(List<T> list);

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
     * 删除
     * @param obj
     * @return
     */
    public int deleteTById(T obj);

    /**
     * 批量删除
     * @param list
     * @return
     */
//    public int deleteBatch(List<String> list);
}
