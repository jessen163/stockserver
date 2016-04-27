package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.dao.StPositionDao;
import com.ryd.business.dto.SearchPositionDTO;
import com.ryd.business.model.StPosition;
import com.ryd.business.service.StPositionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：songby
 * 创建时间：2016/4/27 10:43
 */
public class StPositionServiceImpl implements StPositionService {

    @Autowired
    private StPositionDao stPositionDao;

    @Override
    public boolean savePositionList(List<StPosition> positionList) {
        return false;
    }

    @Override
    public boolean updatePositionAdd(String accountId, String stockId, Long amount) {
        int rs = -1;
        StPosition position = stPositionDao.getPositionByAccountStock(accountId, stockId);
        if(position == null){
            position = new StPosition();
            position.setPositionId(UUIDUtils.uuidTrimLine());
            position.setAccountId(accountId);
            position.setStockId(stockId);
            position.setAmount(amount);

            position.setStatus(ApplicationConstants.STOCK_STPOSITION_STATUS_TRUSTEE.shortValue());

            rs = stPositionDao.add(position);
        }else{
            //原有持仓
            Long camount = position.getAmount();
            //增加持仓
            position.setAmount(camount+amount);
            rs = stPositionDao.update(position);
        }
        return rs > 0;
    }

    @Override
    public boolean updatePositionReduce(String accountId, String stockId, Long amount) {
        StPosition position = stPositionDao.getPositionByAccountStock(accountId, stockId);
        //没有对应股票持仓
        if (position == null) {
            return false;
        }else{
            //原有持仓
            Long camount = position.getMarketAmount();
            if (camount >= amount) {
                //减少持仓
                position.setMarketAmount(camount - amount);

                return stPositionDao.update(position) > 0;
            } else {
                return false;
            }
        }
    }

    @Override
    public List<StPosition> findPositionList(SearchPositionDTO searchPositionDTO) {
        return null;
    }
}
