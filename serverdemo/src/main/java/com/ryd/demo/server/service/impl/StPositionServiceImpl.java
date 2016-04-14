package com.ryd.demo.server.service.impl;

import com.ryd.demo.server.bean.StAccount;
import com.ryd.demo.server.bean.StPosition;
import com.ryd.demo.server.common.Constant;
import com.ryd.demo.server.common.DataConstant;
import com.ryd.demo.server.service.StPositionServiceI;

import java.util.Map;
import java.util.UUID;

/**
 * <p>标题:持仓处理ServiceImpl</p>
 * <p>描述:持仓处理ServiceImpl</p>
 * 包名：com.ryd.stockanalysis.service.impl
 * 创建人：songby
 * 创建时间：2016/3/30 13:36
 */
public class StPositionServiceImpl implements StPositionServiceI {


    @Override
    public boolean operateStPosition(String accountId, String stockId, int amount,int type) {

        //获取对应的帐户信息
        StAccount account = DataConstant.stAccounts.get(accountId);
        //获取对应的持仓
        Map<String,StPosition> stpMap = DataConstant.stAccountPositionMap.get(accountId);

        if(account==null||stpMap==null){return false;}

        StPosition stp = stpMap.get(stockId);

        //如果状态为增加，则是增加仓位
        if(type == Constant.STOCK_STQUOTE_ACCOUNTMONEY_TYPE_ADD.intValue()) {

            //如果对应仓位为空，新建对应这支股票的仓位
            if (stp == null) {
                stp = new StPosition();
                StPosition atPos = new StPosition();
                atPos.setPositionId(UUID.randomUUID().toString());
                atPos.setAccountId(accountId);
                atPos.setStockId(stockId);
                atPos.setStStock(DataConstant.stockTable.get(stockId));
                atPos.setAmount(amount);
                atPos.setStatus(Constant.STOCK_STPOSITION_STATUS_TRUSTEE);

                stpMap.put(stockId,atPos);
            } else {

                //原有持仓
                int camount = stp.getAmount();

                //增加持仓
                stp.setAmount(camount + amount);
            }

            return true;
        }else if(type == Constant.STOCK_STQUOTE_ACCOUNTMONEY_TYPE_REDUSE.intValue()) { //如果状态为减少，则是减少仓位
            //没有对应股票持仓
            if (stp == null) {
                return false;
            }else{
                //原有持仓数量
                int stamount = stp.getAmount();

                if (stamount >= amount) {
                    //减少持仓
                    stp.setAmount(stamount - amount);
                    return true;
                } else {
                    return false;
                }
            }

        }else{
            return  false;
        }
    }

    @Override
    public void calculateAvgPrice(String accountId, String stockId) {

    }

}
