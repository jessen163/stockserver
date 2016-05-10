package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StQuote;

import java.util.List;

/**
 * <p>标题:报价Dao</p>
 * <p>描述:报价Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/22 13:52
 */
public interface StQuoteDao extends BaseDao<StQuote> {

    public int addBatch(List<StQuote> list);

    /**
     *  根据条件状态查询报价
     * @param obj
     * @param statusList
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @return
     */
    public List<StQuote> findStQuoteListByStatus(StQuote obj, List<Short> statusList, Long startTime,Long endTime, int limit, int offset);
}
