package com.ryd.business.dao;

import com.ryd.basecommon.dao.BaseDao;
import com.ryd.business.model.StPosition;

import java.util.List;

/**
 * <p>标题:持仓Dao</p>
 * <p>描述:持仓Dao</p>
 * 包名：com.ryd.business.dao
 * 创建人：songby
 * 创建时间：2016/4/22 13:52
 */
public interface StPositionDao extends BaseDao<StPosition> {

    /**
     * 根据帐户ID和股票ID查询仓位
     * @param accountId
     * @param stockId
     * @return
     */
    public StPosition getPositionByAccountStock(String accountId, String stockId);

    /**
     * 查询数量
     * @return
     */
    public int getCount();


    public int addBatch(List<StPosition> list);

    public int updateBatch(List<StPosition> list);

    public int deleteBatch(List<String> list);

    /**
     * 持有股票数量!=可交易股票数量仓位数
     * @param limit
     * @param offset
     * @return
     */
    public List<StPosition> findAmountListNoEqual(int limit,int offset);
}
