package com.ryd.business.service;

import com.ryd.business.dto.SearchPositionDTO;
import com.ryd.business.model.StPosition;

import java.util.List;

/**
 * <p>标题:仓位Service</p>
 * <p>描述:依赖于账户、股票配置</p>
 * 包名：com.ryd.business.service
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public interface StPositionService {
    /**
     * 保存仓位信息
     * @param positionList
     * @return
     */
    public boolean savePositionList(List<StPosition> positionList);


    /**
     * 仓位结算
     * @param size 执行记录数
     * @return
     */
    public boolean updatePosition(int size) throws Exception;

    /**
     * 增加仓位
     * @param accountId
     * @param stockId
     * @param amount
     * @return
     */
    public boolean updatePositionAdd(String accountId, String stockId, Long amount) throws Exception;

    /**
     * 增加仓位-撤单
     * @param accountId
     * @param stockId
     * @param amount
     * @return
     */
    public boolean updatePositionRevokeAdd(String accountId, String stockId, Long amount) throws Exception;

    /**
     * 减仓
     * @param accountId
     * @param stockId
     * @param amount
     * @return
     */
    public boolean updatePositionReduce(String accountId, String stockId, Long amount) throws Exception;
    /**
     * 查询仓位信息，接收参数
     * @param searchPositionDTO
     * @return
     */
    public List<StPosition> findPositionList(SearchPositionDTO searchPositionDTO);
}